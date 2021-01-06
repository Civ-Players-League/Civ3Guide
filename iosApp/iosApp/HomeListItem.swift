import SwiftUI
import shared

struct HomeListItem: View {
    let destination: HomeDestination
    
    var body: some View {
        HStack {
            Image(destination.image)
                .resizable()
                .scaledToFit()
                .clipShape(/*@START_MENU_TOKEN@*/Circle()/*@END_MENU_TOKEN@*/)
                .overlay(Circle().stroke(Color.black, lineWidth: 0.5))
                .frame(width: 56, height: 56)
            Spacer().frame(width:8)
            VStack(alignment: .leading) {
                Text(destination.title).font(.headline)
                Spacer().frame(height:4)
                if destination.summary != nil {
                    Text(destination.summary!).font(.subheadline)
                }
            }
            Spacer()
        }.frame(minHeight: 72, alignment: .center)
    }
}

struct HomeListItem_Previews: PreviewProvider {
    static var previews: some View {
        HomeListItem(destination: .workerPuzzle)
    }
}
