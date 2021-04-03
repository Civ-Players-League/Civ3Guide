import SwiftUI
import shared

struct TileOutputBreakdown: View {
    let tile: Tile
    let government: Government?
    let breakdown: shared.TileOutputBreakdown
    
    init(
        configuration: WorkerPuzzleConfiguration,
        tile: Tile,
        government: Government? = nil
    ) {
        self.tile = tile
        self.government = government
        
        let isAgricultural = configuration.isAgricultural
        if government == nil {
            self.breakdown = tile.getOutputBreakdown(isAgricultural: isAgricultural)
        } else {
            self.breakdown = government!.getOutputBreakdown(tile: tile, isAgricultural: isAgricultural)
        }
    }
    
    var body: some View {
        VStack {
            HStack {
                Text(tile.terrain.label.load())
                    .fixedSize(horizontal: false, vertical: true)
                    .padding(0)
                Spacer()
                TileOutputSummary(output: breakdown.baseOutput)
            }
            ForEach(breakdown.modifiers, id: \.self) { modifier in
                HStack {
                    Text(modifier.label.load())
                        .fixedSize(horizontal: false, vertical: true)
                        .padding(0)
                    Spacer()
                    TileOutputSummary(output: modifier.effect, isAdditive: true)
                }
            }
            if breakdown.modifiers.count > 0 {
                VStack {
                    Divider()
                    HStack {
                        Text(MR.strings().total)
                            .fixedSize(horizontal: false, vertical: true)
                            .padding(0)
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
            configuration: WorkerPuzzles().all.first!,
            tile: MapConfigurations().all.first!.tiles[4].tile
                .withAction(workerAction: .irrigate).withAction(workerAction: .road),
            government: StandardGovernment.republic
        )
    }
}
