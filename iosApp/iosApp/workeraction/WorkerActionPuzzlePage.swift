import SwiftUI
import shared

struct WorkerActionPuzzlePage: View {
    let puzzleIndex: Int
    @Binding var isNavigationActive : Bool
    @State var selectedTile: TileInfo?
    @State var selectedAction: WorkerAction?
    @State private var goToNext: Bool = false
    @State private var showHelp: Bool = false
    
    private let numPuzzlesPerRow = Int(LevelManager().PUZZLES_PER_ROW)
    
    var isLastPuzzle: Bool {
        puzzleIndex + 1 >= WorkerPuzzles().all.count ||
            puzzleIndex % numPuzzlesPerRow == numPuzzlesPerRow - 1
    }
    
    var configuration: WorkerPuzzleConfiguration {
        WorkerPuzzles().all[puzzleIndex]
    }
    
    var isSolved: Bool {
        let isSolved = configuration.isOptimal(tile: selectedTile, action: selectedAction)
        if isSolved {
            WorkerPuzzleProgressManager().notePuzzleSolved(index: Int32(puzzleIndex))
        }
        return isSolved
    }
    
    var body: some View {
        GeometryReader { geo in
            ScrollView {
                ZStack {
                    Spacer()
                    VStack {
                        Text(MR.strings().puzzle_header_prompt).font(.headline)
                        MapView(
                            map: configuration.map,
                            geo: geo,
                            selectedTile: Binding(
                                get: { selectedTile },
                                set: { value in
                                    selectedTile = value
                                    selectedAction = nil
                                }
                            ))
                        selectedTile.map { tile in
                            buildTileArea(tile)
                        }
                        Spacer()
                        NavigationLink(
                            destination: WorkerActionPuzzlePage(
                                puzzleIndex: puzzleIndex + 1,
                                isNavigationActive: $isNavigationActive
                            ),
                            isActive: $goToNext,
                            label: { EmptyView() }
                        ).isDetailLink(false)
                    }
                }
            }
        }
        .sheet(isPresented: $showHelp) {
            PuzzleHelpPage()
        }
        .padding()
        .navigationBarTitle(
            Text(getLevelDescription()),
            displayMode: .inline
        )
        .navigationBarItems(trailing: Button(MR.strings().help.load()) {
            showHelp = true
        })
        
    }
    
    @ViewBuilder
    private func buildTileArea(_ tile: TileInfo) -> some View {
        VStack(alignment: .center) {
            WorkerActionButtonGroup(
                tile: tile.tile,
                selectedAction: $selectedAction
            )
            
            PuzzleChoiceSummaryView(
                configuration: configuration,
                government: StandardGovernment.despotism,
                tile: tile.tile,
                action: selectedAction,
                isOptimal: isSolved
            )
            
            if (selectedAction != nil) {
                Spacer().frame(height: 16)
                if isSolved && configuration.extraExplanation != nil {
                    Text("\(MR.strings().worker_action_optimal.load()).\n\(configuration.extraExplanation!)")
                        .multilineTextAlignment(.center)
                } else {
                    Text(
                        isSolved
                            ? MR.strings().worker_action_optimal
                            : MR.strings().worker_action_not_optimal)
                }
            }
            if (isSolved) {
                Button(isLastPuzzle ? MR.strings().done.load() : MR.strings().next.load()) {
                    
                    if (isLastPuzzle) {
                        isNavigationActive = false
                    } else {
                        goToNext = true
                    }
                }.padding()
            }
        }
    }
    
    private func getLevelDescription() -> String {
        let level = puzzleIndex / numPuzzlesPerRow
        let data = WorkerPuzzleProgressManager().getLevelPageData()
        let rowData = data.rows[level]
        let totalForLevel = rowData.total
        return Levels.getLevelDescription(
            index: puzzleIndex,
            total: Int(totalForLevel)
        )
    }
}

struct WorkerActionPuzzlePage_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            WorkerActionPuzzlePage(
                puzzleIndex: 0,
                isNavigationActive: .constant(false),
                selectedTile: WorkerPuzzles().all.first!.map.tiles[5],
                selectedAction: .mine
            )
        }
    }
}
