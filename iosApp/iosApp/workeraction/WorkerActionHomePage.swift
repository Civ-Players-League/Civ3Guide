import SwiftUI
import shared

struct WorkerActionHomePage: View {
    
    @Binding var isNavigationActive : Bool
    @State private var activeLevelIndex: Int?
    @State private var initialNavigationChecked: Bool = false
        
    var body: some View {
        createContent(WorkerPuzzleProgressManager().getLevelPageData())
    }
    
    @ViewBuilder
    private func createContent(_ data: WorkerPuzzleLevelPageData) -> some View {
        VStack {
            ForEach(0..<data.rows.count, id: \.self) { rowIndex in
                NavigationLink(
                    destination: WorkerActionPuzzlePage(
                        puzzleIndex: Int(data.rows[rowIndex].launchIndex),
                        isNavigationActive: Binding(
                            get: { activeLevelIndex == rowIndex },
                            set: { isActive in
                                activeLevelIndex = isActive ? rowIndex : nil
                            }
                        )
                    ),
                    isActive: Binding(
                        get: { activeLevelIndex == rowIndex },
                        set: { isActive in
                            activeLevelIndex = isActive ? rowIndex : nil
                        }
                    )
                ) {
                    EmptyView()
                }
                .isDetailLink(false)
            }
            Text(MR.strings().worker_puzzle_intro_text)
                .padding()
        }
        List {
            ForEach(0..<data.rows.count, id: \.self) { rowIndex in
                Button(action: { activeLevelIndex = rowIndex }) {
                    createRow(rowIndex, data.rows[rowIndex])
                }
                .disabled(data.rows[rowIndex].isLocked)
            }
        }
        .navigationBarTitle(
            Text(MR.strings().home_label_worker_action_title),
            displayMode: .inline
        )
    }
    
    @ViewBuilder
    private func createRow(_ index: Int, _ row: WorkerPuzzleLevelPageRowData) -> some View {
        LevelRow(
            index: index,
            isLocked: row.isLocked,
            completedItemCount: Int(row.completed),
            itemCount: Int(row.total)
        )
    }
}

struct WorkerActionHomePage_Previews: PreviewProvider {
    static var previews: some View {
        WorkerActionHomePage(isNavigationActive: .constant(true))
    }
}
