import SwiftUI
import shared

struct CityPlacementView: View {
    let puzzleIndex: Int
    @Binding var isNavigationActive: Bool
    
    @State private var goToNext: Bool = false
    @State private var selectedTile: TileInfo?
    @State private var showHelp: Bool = false
    
    private let numPuzzlesPerRow = Int(LevelManager().PUZZLES_PER_ROW)
    
    var puzzle: CityPlacementPuzzle {
        CityPlacementPuzzle.Companion().all[puzzleIndex]
    }
    
    var isLastPuzzle: Bool {
        puzzleIndex >= CityPlacementPuzzle.Companion().all.count - 1 ||
            puzzleIndex % numPuzzlesPerRow == numPuzzlesPerRow - 1
    }
    
    var selectedTileAnswer: CityPlacementAnswer? {
        selectedTile.map { tile in
            puzzle.getAnswer(tile: tile)
        }
    }
    
    var body: some View {
        GeometryReader { geo in
            ScrollView {
                VStack {
                    Text(MR.strings().city_placement_prompt).font(.headline)
                    
                    MapView(
                        map: puzzle.map,
                        geo: geo,
                        highlightSelectableTiles: true,
                        selectedTile: Binding(
                            get: { selectedTile },
                            set: { value in
                                selectedTile = value
                                if selectedTileAnswer?.isCorrect ?? false {
                                    CityPlacementProgressManager().notePuzzleSolved(index: Int32(puzzleIndex))
                                }
                            }
                        )
                    )
                    
                    if let answer = selectedTileAnswer {
                        Spacer().frame(height: 16)
                        Text(answer.explanation)
                            .multilineTextAlignment(.center)
                            .foregroundColor(answer.isCorrect ? .green : .red)
                        
                        if (answer.isCorrect) {
                            Spacer().frame(height: 16)
                            Button(
                                (isLastPuzzle ? MR.strings().done : MR.strings().next).load()
                            ) {
                                if isLastPuzzle {
                                    isNavigationActive = false
                                } else {
                                    goToNext = true
                                }
                            }
                        }
                    }
                    
                    NavigationLink(
                        destination: CityPlacementView(
                            puzzleIndex: puzzleIndex + 1,
                            isNavigationActive: $isNavigationActive
                        ),
                        isActive: $goToNext,
                        label: { EmptyView() }
                    ).isDetailLink(false)
                }
            }
        }
        .navigationBarTitle(Text(getLevelDescription()), displayMode: .inline)
        .navigationBarItems(trailing: Button(MR.strings().help.load()) {
            showHelp = true
        })
        .sheet(isPresented: $showHelp) {
            HelpPage(
                title: MR.strings().city_placement_title,
                message: MR.strings().city_placement_help_text
            )
        }
        .padding()
    }
    
    private func getLevelDescription() -> String {
        let level = puzzleIndex / numPuzzlesPerRow
        let data = CityPlacementProgressManager().getLevelPageData()
        let rowData = data.rows[level]
        let totalForLevel = rowData.total
        return Levels.getLevelDescription(
            index: puzzleIndex,
            total: Int(totalForLevel)
        )
    }
}
