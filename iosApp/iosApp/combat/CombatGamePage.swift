//
//  CombatGamePage.swift
//  Civ III Wizard
//
//  Created by Steven Kideckel on 2021-02-16.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CombatGamePage: View {
    
    @Binding var isNavigationActive: Bool
    
    @State private var showFirstTimePage: Bool
    
    private let sampleEngagement: Engagement = Engagement(
        attacker: MilitaryUnit(rank: .veteran, type: StandardUnitType.warrior, isFortified: false, damage: 0),
        defender: MilitaryUnit(rank: .veteran, type: StandardUnitType.spearman, isFortified: false, damage: 0),
        terrain: .grassland,
        acrossRiver: false,
        cityDefenceBonus: nil
    )
    
    init(isNavigationActive: Binding<Bool>) {
        self._isNavigationActive = isNavigationActive
        self._showFirstTimePage = State(initialValue: CombatPuzzles().shouldShowFirstTimePage())
    }
    
    var body: some View {
        if showFirstTimePage {
            GeometryReader { geo in
                ScrollView {
                    VStack {
                        HStack(alignment: .top) {
                            CombatUnitView(engagement: sampleEngagement, isAttacker: true, size: geo.size.width / 3,
                                           showStats: false)
                            CombatUnitView(engagement: sampleEngagement, isAttacker: false, size: geo.size.width / 3, showStats: false)
                        }
                        Text(MR.strings().combat_odds_first_time_explanation)
                        Spacer().frame(height: 16)
                        Button(MR.strings().lets_go.load()) {
                            withAnimation {
                                CombatPuzzles().noteFirstTimePageSeen()
                                showFirstTimePage = false
                            }
                        }
                    }.padding()
                }
            }.navigationBarTitle(Text(""), displayMode: .inline)
        } else {
            CombatGameView(isNavigationActive: $isNavigationActive)
        }
    }
}

struct CombatGamePage_Previews: PreviewProvider {
    static var previews: some View {
        CombatGamePage(isNavigationActive: .constant(true))
    }
}
