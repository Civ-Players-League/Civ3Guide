import SwiftUI
import shared

struct HomeView: View {
    let multiplayerLink = URL(string: "https://civplayersciv3league.com")
    let feedbackLink = URL(string: "mailto:sixbynineapps@gmail.com")
    
    let destinations = HomeDestination.Companion().getAll()
    @State private var isWorkerActionNavigationActive: Bool = false
    @State private var isCityPlacementNavigationActive: Bool = false
    @State private var isCombatGameNavigationActive: Bool = false
    
    init() {
        UITableView.appearance().backgroundColor = UIColor.clear
        UITableViewCell.appearance().selectionStyle = .none
    }
    
    var body: some View {
        GeometryReader { g in
            ScrollView {
                VStack {
                    Text(MR.strings().app_description).padding()
                    
                    NavigationLink(
                        destination: WorkerActionHomePage(),
                        isActive: $isWorkerActionNavigationActive
                    ) {
                        EmptyView()
                    }
                    .isDetailLink(false)
                    
                    NavigationLink(
                        destination: CityPlacementHomePage(),
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
                        Button(action: { isCombatGameNavigationActive = true}) {
                            HomeListItem(destination: .combatOdds)
                        }
                        
                        Button(action: { isWorkerActionNavigationActive = true}) {
                            HomeListItem(destination: .workerPuzzle)
                        }
                        
                        Button(action: { isCityPlacementNavigationActive = true}) {
                            HomeListItem(destination: .cityPlacement)
                        }
                    }.frame(width: g.size.width - 16, height: g.size.height - 10, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
                }
            }
        }
        .navigationBarTitle(MR.strings().app_name_short, displayMode: .large)
        .navigationBarItems(trailing: createMenu())
    }
    
    @ViewBuilder
    private func createMenu() -> some View {
        if (multiplayerLink != nil || feedbackLink != nil) {
            if #available(iOS 14.0, *) {
                Menu(
                    content: {
                        if let url = multiplayerLink {
                            Button(MR.strings().multiplayer.load()) {
                                UIApplication.shared.open(url)
                            }
                        }
                        if let email = feedbackLink {
                            Button(MR.strings().send_feedback.load()) {
                                UIApplication.shared.open(email)
                            }
                        }
                    },
                    label: {
                        Image(systemName: "ellipsis.circle")
                            .resizable()
                            .scaledToFit()
                            .padding(4)
                            .frame(width: 32, height: 32, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
                    }
                )
            } else {
                EmptyView()
            }
        } else {
            EmptyView()
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
