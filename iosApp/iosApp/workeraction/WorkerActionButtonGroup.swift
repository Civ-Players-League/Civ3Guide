import SwiftUI
import shared

struct WorkerActionButtonGroup: View {
    let tile: Tile
    @Binding var selectedAction: WorkerAction?
    
    var body: some View {
        HStack {
            ForEach(tile.getAvailableActions(), id: \.self) { action in
                WorkerActionButton(
                    action: action,
                    isChecked: action == selectedAction,
                    onClick: {
                        withAnimation {
                            if (action == selectedAction) {
                                selectedAction = nil
                            } else {
                                selectedAction = action
                            }
                        }
                })
            }
        }
    }
}

struct WorkerActionButtonGroup_Previews: PreviewProvider {
    static var previews: some View {
        WorkerActionButtonGroup(
            tile: WorkerPuzzles().all.first!.map.tiles.first!.tile,
            selectedAction: .constant(nil)
        )
    }
}
