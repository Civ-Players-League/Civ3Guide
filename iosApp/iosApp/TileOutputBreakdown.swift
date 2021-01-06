import SwiftUI
import shared

struct TileOutputBreakdown: View {
    let tile: Tile
    let government: Government?
    let breakdown: shared.TileOutputBreakdown
    
    
    init(tile: Tile, government: Government? = nil) {
        self.tile = tile
        self.government = government
        if government == nil {
            self.breakdown = tile.getOutputBreakdown()
        } else {
            self.breakdown = government!.getOutputBreakdown(tile: tile)
        }
    }
    
    var body: some View {
        VStack {
            HStack {
                Text(tile.terrain.label.load())
                Spacer()
                TileOutputSummary(output: breakdown.baseOutput)
            }
            ForEach(breakdown.modifiers, id: \.self) { modifier in
                HStack {
                    Text(modifier.label.load())
                    Spacer()
                    TileOutputSummary(output: modifier.effect, isAdditive: true)
                }
            }
            if breakdown.modifiers.count > 0 {
                VStack {
                    Divider()
                    HStack {
                        Text(MR.strings().total)
                        Spacer()
                        TileOutputSummary(output: breakdown.totalOutput)
                    }
                }
            }
        }
    }
}

struct TileOutputBreakdown_Previews: PreviewProvider {
    static var previews: some View {
        TileOutputBreakdown(
            tile: MapConfigurations().all.first!.tiles[4].tile
                .withAction(workerAction: .irrigate).withAction(workerAction: .road),
            government: StandardGovernment.republic
        )
    }
}
