import SwiftUI
import shared

struct CityPlacementView: View {
  let puzzleIndex: Int
  @Binding var isNavigationActive: Bool

  @State private var goToNext: Bool = false
  @State private var selectedTile: TileInfo?

  var puzzle: CityPlacementPuzzle {
    CityPlacementPuzzle.Companion().all[puzzleIndex]
  }

  var isLastPuzzle: Bool {
    puzzleIndex >= CityPlacementPuzzle.Companion().all.count - 1
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
              selectedTile: Binding(
                  get: { selectedTile },
                  set: { value in
                    selectedTile = value
                  }
              )
          )

          if let answer = selectedTileAnswer {
            Spacer().frame(height: 16)
            Text(answer.explanation)
                .multilineTextAlignment(.center)
                .foregroundColor(answer.isCorrect ? .green : .red)
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
        .navigationBarTitle(Text(MR.strings().puzzle_index, puzzleIndex + 1), displayMode: .inline)
        .navigationBarItems(
            trailing: Button(
                (isLastPuzzle ? MR.strings().done : MR.strings().next).load()
            ) {
              if isLastPuzzle {
                isNavigationActive = false
              } else {
                goToNext = true
              }
            }
                .disabled(!(selectedTileAnswer?.isCorrect ?? false))
        )
        .padding()
  }
}
