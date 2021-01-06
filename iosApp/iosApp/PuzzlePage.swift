import SwiftUI
import shared

struct PuzzlePage: View {
    let puzzleIndex: Int
    @Binding var isNavigationActive : Bool
    @State var selectedTile: TileInfo?
    @State var selectedAction: WorkerAction?
    @State private var goToNext: Bool = false
    @State private var showHelp: Bool = false
    
    var isLastPuzzle: Bool {
        puzzleIndex + 1 >= WorkerPuzzles().all.count
    }
    
    var configuration: WorkerPuzzleConfiguration {
        WorkerPuzzles().all[puzzleIndex]
    }
    
    var isSolved: Bool {
        configuration.isOptimal(tile: selectedTile, action: selectedAction)
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
                            destination: PuzzlePage(
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
        .navigationBarTitle(Text(MR.strings().puzzle_index, puzzleIndex + 1), displayMode: .inline)
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
                government: StandardGovernment.despotism,
                tile: tile.tile,
                action: selectedAction,
                isOptimal: isSolved
            )
            
            if (selectedAction != nil) {
                Spacer().frame(height: 16)
                Text(isSolved ? MR.strings().worker_action_optimal : MR.strings().worker_action_not_optimal)
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
}

struct PuzzlePage_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            PuzzlePage(
                puzzleIndex: 0,
                isNavigationActive: .constant(false),
                selectedTile: WorkerPuzzles().all.first!.map.tiles[5],
                selectedAction: .mine
            )
        }
    }
}
