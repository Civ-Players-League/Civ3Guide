import SwiftUI
import shared

struct WorkerActionButton: View {
    let action: WorkerAction
    let isChecked: Bool
    let onClick: () -> Void
    
    var body: some View {
        Button(action: onClick) {
            Image(action.image).resizable().scaledToFit()
                .padding(4.0)
        }
        .background(Circle().fill(Color.blue).opacity(isChecked ? 1 : 0))
        .shadow(radius: /*@START_MENU_TOKEN@*/10/*@END_MENU_TOKEN@*/)
        .frame(width: 44, height: 44)
    }
}

struct WorkerActionButton_Previews: PreviewProvider {
    static var previews: some View {
        WorkerActionButton(action: WorkerAction.clearForest, isChecked: true) {
            
        }
    }
}
