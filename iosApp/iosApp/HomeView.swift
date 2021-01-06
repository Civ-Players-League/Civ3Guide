import SwiftUI
import shared

struct HomeView: View {
    let destinations = HomeDestination.Companion().getAll()
    @State private var isQuizNavigationActive: Bool = false
    @State private var isWorkerActionNavigationActive: Bool = false
    
    var body: some View {
        VStack {
            Text(MR.strings().app_description)
            List {
                NavigationLink(
                    destination: QuizView(isNavigationActive: $isQuizNavigationActive),
                    isActive: $isQuizNavigationActive
                ) {
                    HomeListItem(destination: HomeDestination.quiz)
                }.isDetailLink(false)
                
                NavigationLink(
                    destination: PuzzlePage(puzzleIndex: 0, isNavigationActive: $isWorkerActionNavigationActive),
                    isActive: $isWorkerActionNavigationActive
                ) {
                    HomeListItem(destination: HomeDestination.workerPuzzle)
                }.isDetailLink(false)
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
