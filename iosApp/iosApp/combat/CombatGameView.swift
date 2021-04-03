import SwiftUI
import shared

struct CombatGameView: View {
    
    @Binding var isNavigationActive: Bool
    
    @State private var engagement: Engagement = EngagementKt.randomEngagement(
        allowUniqueUnits: true,
        allowFastUnits: true,
        allowRetreat: false
    )
    
    @State private var selectedLikelyToWinOption: Double = 2.0
    @State private var selectedFavorabilityOption: Double = 2.0
    @State private var likelyToWinButton: TriStateBool = .none
    @State private var favorabilityButton: TriStateBool = .none
    @State private var showStats: Bool = false
    @State private var showExplanation: Bool = false
    
    var body: some View {
        GeometryReader { geo in
            ScrollView {
                buildContent(geo)
            }
        }
        .navigationBarTitle(Text(""), displayMode: .inline)
        .navigationBarItems(
            trailing: Button(showStats ? "Hide stats": "Show stats") {
                withAnimation {
                    showStats = !showStats
                }
            }
        )
    }
    
    var showResult: Bool {
        likelyToWinButton != .none && favorabilityButton != .none
    }
    
    private var combatResults: CombatResults {
        CombatCalculator().calculateCombatResults(engagement: engagement)
    }
    
    @ViewBuilder
    private func buildContent(_ geo: GeometryProxy) -> some View {
        VStack(alignment: .center) {
            HStack(alignment: .top) {
                CombatUnitView(
                    engagement: engagement,
                    isAttacker: true,
                    size: geo.size.width / 3,
                    showStats: showStats
                )
                
                if (engagement.acrossRiver) {
                    VStack {
                        Spacer().frame(height: 56)
                        Image(MR.images().river)
                            .renderingMode(.template)
                            .resizable()
                            .scaledToFit()
                            .foregroundColor(.blue)
                            .frame(width: 24, height: 100)
                        Text(MR.strings().river).font(.footnote)
                    }
                    
                } else {
                    Spacer().frame(width: 24)
                }
                
                CombatUnitView(
                    engagement: engagement,
                    isAttacker: false,
                    size: geo.size.width / 3,
                    showStats: showStats
                )
            }
            
            Spacer().frame(height: 16)
            
            VStack {
                Text(MR.strings().attacker_win_probability_prompt)
                    .font(.headline)
                
                HStack {
                    Button(action: { withAnimation { likelyToWinButton = .no }}) {
                        Text(MR.strings().no)
                            .foregroundColor(getLikelyToWinButtonColor(.no))
                            .padding()
                            .overlay(
                                    RoundedRectangle(cornerRadius: 16)
                                        .stroke(getLikelyToWinButtonColor(.no), lineWidth: 2)
                                )
                    }
                    
                    Spacer().frame(width: 64)
                    
                    Button(action: { withAnimation { likelyToWinButton = .yes }}) {
                        Text(MR.strings().yes)
                            .foregroundColor(getLikelyToWinButtonColor(.yes))
                            .padding()
                            .overlay(
                                    RoundedRectangle(cornerRadius: 16)
                                        .stroke(getLikelyToWinButtonColor(.yes), lineWidth: 2)
                                )
                    }
                }
            }
            
            VStack {
                Spacer().frame(height: 24)
                
                Text(MR.strings().combat_odds_prompt).font(.headline)
                
                HStack {
                    Button(action: { withAnimation { favorabilityButton = .no }}) {
                        Text(MR.strings().no)
                            .foregroundColor(getFavorabilityButtonColor(.no))
                            .padding()
                            .overlay(
                                    RoundedRectangle(cornerRadius: 16)
                                        .stroke(getFavorabilityButtonColor(.no), lineWidth: 2)
                                )
                    }
                    
                    Spacer().frame(width: 64)
                    
                    Button(action: { withAnimation { favorabilityButton = .yes }}) {
                        Text(MR.strings().yes)
                            .foregroundColor(getFavorabilityButtonColor(.yes))
                            .padding()
                            .overlay(
                                    RoundedRectangle(cornerRadius: 16)
                                        .stroke(getFavorabilityButtonColor(.yes), lineWidth: 2)
                                )
                    }
                }
            }
            
            buildSummary()
            
            VStack {
                Spacer().frame(height: 16)
                
                if showResult {
                    Button(MR.strings().new_puzzle.load()) {
                        withAnimation {
                            engagement = getNewEngagement()
                            selectedLikelyToWinOption = 2.0
                            selectedFavorabilityOption = 2.0
                            likelyToWinButton = .none
                            favorabilityButton = .none
                            showStats = false
                        }
                    }
                    
                    Spacer().frame(height: 24)
                    
                    Button(MR.strings().explain.load()) {
                        withAnimation {
                            showExplanation = true
                        }
                    }
                    .sheet(isPresented: $showExplanation) {
                        ScrollView(showsIndicators: false) {
                            VStack {
                                Text(MR.strings().combat_explanation).font(/*@START_MENU_TOKEN@*/.title/*@END_MENU_TOKEN@*/)
                                Spacer().frame(height: 8)
                                Text(CombatExplainer().explain(engagement: engagement))
                            }.padding()
                        }
                    }
                }
               
            }
        }
        .frame(maxWidth: .infinity)
        .padding()
    }
    
    private func getLikelyToWinButtonColor(_ which: TriStateBool) -> Color {
        if which == likelyToWinButton {
            if showResult {
                let pWin = CombatCalculator().calculateCombatResults(engagement: engagement).p(result: .attackerWins)
                let isCorrect = (pWin <= 0.5 && which == .no) || (pWin >= 0.5 && which == .yes)
                return isCorrect ? .green : .red
            } else {
                return .accentColor
            }
        }
        return .primary
    }
    
    private func getFavorabilityButtonColor(_ which: TriStateBool) -> Color {
        if which == favorabilityButton {
            if showResult {
                let favorability = CombatCalculator().calculateCombatResults(engagement: engagement).attackerFavorability
                let isCorrect = (favorability <= 1.0 && which == .no) || (favorability >= 1.0 && which == .yes)
                return isCorrect ? .green : .red
            } else {
                return .accentColor
            }
        }
        return .primary
    }
    
    private func getNewEngagement() -> Engagement {
        return EngagementKt.randomEngagement(
            allowUniqueUnits: true,
            allowFastUnits: true,
            allowRetreat: false
        )
    }
    
    private func getWinProbSubtitle() -> String {
        switch Int(selectedLikelyToWinOption) {
        case 0:
            return MR.strings().combat_odds_very_unlikely.load()
        case 1:
            return MR.strings().combat_odds_unlikely.load()
        case 2:
            return MR.strings().combat_odds_even_probability.load()
        case 3:
            return MR.strings().combat_odds_likely.load()
        case 4:
            return MR.strings().combat_odds_very_likely.load()
        default:
            return ""
        }
    }
    
    private func getCorrentWinProbSubtitle() -> String {
        switch getCorrectWinProbabilityIndex() {
        case 0:
            return MR.strings().combat_odds_very_unlikely.load()
        case 1:
            return MR.strings().combat_odds_unlikely.load()
        case 2:
            return MR.strings().combat_odds_even_probability.load()
        case 3:
            return MR.strings().combat_odds_likely.load()
        case 4:
            return MR.strings().combat_odds_very_likely.load()
        default:
            return ""
        }
    }
    
    private func getFavorabilitySubtitle() -> String {
        switch Int(selectedFavorabilityOption) {
        case 0:
            return MR.strings().combat_odds_very_unfavorable.load()
        case 1:
            return MR.strings().combat_odds_unfavorable.load()
        case 2:
            return MR.strings().combat_odds_even.load()
        case 3:
            return MR.strings().combat_odds_favorable.load()
        case 4:
            return MR.strings().combat_odds_very_favorable.load()
        default:
            return ""
        }
    }
    
    private func getCorrectFavorabilitySubtitle() -> String {
        switch getCorrectFavorabilityIndex() {
        case 0:
            return MR.strings().combat_odds_very_unfavorable.load()
        case 1:
            return MR.strings().combat_odds_unfavorable.load()
        case 2:
            return MR.strings().combat_odds_even.load()
        case 3:
            return MR.strings().combat_odds_favorable.load()
        case 4:
            return MR.strings().combat_odds_very_favorable.load()
        default:
            return ""
        }
    }
    
    private func getWinProbability() -> Double {
        return CombatCalculator().calculateCombatResults(engagement: engagement).p(result: .attackerWins)
    }
    
    @ViewBuilder
    private func buildSummary() -> some View {
        if showResult {
            VStack {
                Divider()
                Spacer().frame(height: 8)
                
                Text(MR.strings().p_win, formatPercentage(getWinProbability()))
                HStack {
                    Text("Cost: \(engagement.attacker.type.cost)")
                    Image(MR.images().shield)
                        .resizable()
                        .frame(width: 16, height: 18)
                    Text("vs \(engagement.defender.type.cost)")
                    Image(MR.images().shield)
                        .resizable()
                        .frame(width: 16, height: 18)
                }
                
                Text(MR.strings().expected_shields, formatPosNeg1DecimalPoint(combatResults.expectedShields))
                Text("(\(getSummaryFavorabilityText()))")
            }
        } else {
            EmptyView()
        }
    }
    
    private func getSummaryFavorabilityText() -> String {
        let favorability = CombatCalculator().calculateCombatResults(engagement: engagement).attackerFavorability
        if favorability < 0.2 {
            return "Very unfavourable"
        } else if favorability < 0.8 {
            return "Unfavourable"
        } else if favorability < 1.0 {
            return "Slightly unfavourable"
        } else if favorability == 1.0 {
            return "Even"
        } else if favorability < 1.2 {
            return "Slightly favourable"
        } else if favorability < 2.0 {
            return "Favourable"
        } else {
            return "Very favourable"
        }
    }
    
    private func formatPercentage(_ value: Double) -> String {
        return "\(Int(value * 100))%"
    }
    
    private func formatPosNeg1DecimalPoint(_ value: Double) -> String {
        let oneDpString = "\(Double(Int(value * 10)) / 10.0)"
        if value > 0 {
            return "+\(oneDpString)"
        } else {
            return oneDpString
        }
    }
    
    private func getCorrectWinProbabilityIndex() -> Int {
        let pWin = CombatCalculator().calculateCombatResults(engagement: engagement).p(result: .attackerWins)
        if pWin < 0.2 {
            return 0
        } else if pWin < 0.4 {
            return 1
        } else if pWin < 0.6 {
            return 2
        } else if pWin < 0.8 {
            return 3
        } else {
            return 4
        }
    }
    
    private func isAnswerCorrect(answer: Int, correct: Int) -> Bool {
        switch answer {
        case 0:
            return correct == 0
        case 1:
            return correct <= 1
        case 2:
            return correct == 2
        case 3:
            return correct >= 3
        case 4:
            return correct == 4
        default:
            return false
        }
    }
    
    private func getCorrectFavorabilityIndex() -> Int {
        let favorability = CombatCalculator().calculateCombatResults(engagement: engagement).attackerFavorability
        if favorability < 0.5 {
            return 0
        } else if favorability < 0.8 {
            return 1
        } else if favorability < 1.2 {
            return 2
        } else if favorability < 2 {
            return 3
        } else {
            return 4
        }
    }
    
    enum TriStateBool {
        case none
        case no
        case yes
    }
}

struct CombatGameView_Previews: PreviewProvider {
    static var previews: some View {
        CombatGameView(
            isNavigationActive: .constant(true)
        
        )
    }
}
