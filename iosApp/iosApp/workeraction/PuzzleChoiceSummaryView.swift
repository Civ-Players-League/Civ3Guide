import SwiftUI
import shared

struct PuzzleChoiceSummaryView: View {
    let configuration: WorkerPuzzleConfiguration
    let government: Government?
    let tile: Tile
    let action: WorkerAction?
    let isOptimal: Bool
    @State private var isExpanded: Bool = false
    
    private var effectiveTile: Tile {
        guard let nonNullAction = action else { return tile }
        return tile.withAction(workerAction: nonNullAction)
    }
    
    var body: some View {
        VStack{
            Spacer().frame(height: 8)
            TileOutputBreakdown(
                configuration: configuration,
                tile: effectiveTile,
                government: government
            )
        }
    }
}

struct PuzzleChoiceSummaryView_Previews: PreviewProvider {
    static var previews: some View {
        PuzzleChoiceSummaryView(
            configuration: WorkerPuzzles().all.first!,
            government: StandardGovernment.despotism,
            tile: MapConfigurations().all.first!.tiles[0].tile,
            action: .mine,
            isOptimal: false
        )
    }
}
