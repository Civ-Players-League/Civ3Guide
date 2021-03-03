import SwiftUI
import shared

struct TileOutputSummary: View {
    let output: TileOutput
    let isAdditive: Bool
    
    init(output: TileOutput) {
        self.output = output
        self.isAdditive = false
    }
    
    init(output: TileOutput, isAdditive: Bool) {
        self.output = output
        self.isAdditive = isAdditive
    }
    
    var body: some View {
        HStack {
            item(output.food, MR.images().food)
            item(output.shields, MR.images().shield)
            item(output.commerce, MR.images().commerce)
        }
    }
    
    @ViewBuilder
    private func item(_ value: Int32, _ image: ResourcesImageResource) -> some View {
        HStack {
            Text((isAdditive && value > 0) ? "+\(value)" : "\(value)")
            Image(image)
                .resizable()
                .scaledToFit()
                .frame(width: 20, height: 20)
        }.frame(width: 60, alignment: .trailing)
        .hidden(isAdditive && value == 0)
    }
}

struct TileOutputSummary_Previews: PreviewProvider {
    static var previews: some View {
        TileOutputSummary(
            output: TileOutput(food: 1, shields: 0, commerce: 2, defenceBonus: 1.0),
            isAdditive: true
        )
    }
}
