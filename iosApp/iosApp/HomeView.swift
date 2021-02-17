import SwiftUI
import shared

struct HomeView: View {
    let destinations = HomeDestination.Companion().getAll()
    @State private var isQuizNavigationActive: Bool = false
    @State private var isWorkerActionNavigationActive: Bool = false
    @State private var isCityPlacementNavigationActive: Bool = false
    @State private var isCombatGameNavigationActive: Bool = false

    init() {
        UITableView.appearance().backgroundColor = UIColor.clear
        UITableViewCell.appearance().selectionStyle = .none
    }
    
    var body: some View {
        VStack {
            Text(MR.strings().app_description).padding()
            NavigationLink(
                    destination: QuizView(isNavigationActive: $isQuizNavigationActive),
                    isActive: $isQuizNavigationActive
            ) {
                EmptyView()
            }.isDetailLink(false)
            
            NavigationLink(
                destination: PuzzlePage(puzzleIndex: 0, isNavigationActive: $isWorkerActionNavigationActive),
                isActive: $isWorkerActionNavigationActive
            ) {
                EmptyView()
            }
            .isDetailLink(false)
            
            NavigationLink(
                    destination: CityPlacementView(puzzleIndex: 0, isNavigationActive: $isCityPlacementNavigationActive),
                    isActive: $isCityPlacementNavigationActive
            ) {
                EmptyView()
            }
            .isDetailLink(false)
            
            NavigationLink(
                    destination: CombatGamePage(
                        isNavigationActive: $isCombatGameNavigationActive
                    ),
                    isActive: $isCombatGameNavigationActive
            ) {
                EmptyView()
            }
            .isDetailLink(false)

            List {
                Button(action: { isQuizNavigationActive = true}) {
                    HomeListItem(destination: .quiz)
                }
                
                Button(action: { isWorkerActionNavigationActive = true}) {
                    HomeListItem(destination: .workerPuzzle)
                }
                
                Button(action: { isCityPlacementNavigationActive = true}) {
                    HomeListItem(destination: .cityPlacement)
                }
                
                Button(action: { isCombatGameNavigationActive = true}) {
                    HomeListItem(destination: .combatOdds)
                }
            }.navigationBarTitle(MR.strings().app_name, displayMode: .large)
        }
        
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            HomeView()
        }
    }
}
