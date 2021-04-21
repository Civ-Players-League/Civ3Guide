import SwiftUI
import shared

struct WorkerActionHomePage: View {
    
    @State private var data = WorkerPuzzleProgressManager().getLevelPageData()
    
    var body: some View {
        LevelList(data, header: MR.strings().worker_puzzle_intro_text) { launchIndex, isNavigationActive in
            WorkerActionPuzzlePage(
                puzzleIndex: Int(launchIndex),
                isNavigationActive: isNavigationActive
            )
        }
        .navigationBarTitle(
            Text(MR.strings().home_label_worker_action_title),
            displayMode: .inline
        )
        .onAppear {
            withAnimation {
                data = WorkerPuzzleProgressManager().getLevelPageData()
            }
        }
    }
}

struct WorkerActionHomePage_Previews: PreviewProvider {
    static var previews: some View {
        WorkerActionHomePage()
    }
}
