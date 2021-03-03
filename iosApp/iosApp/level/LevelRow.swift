import SwiftUI
import shared

struct LevelRow: View {
    
    let index: Int
    let isLocked: Bool
    let completedItemCount: Int
    let itemCount: Int
    
    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                Text(MR.strings().level_x.format(int: Int32(index + 1)))
                    .font(.headline)
                    .foregroundColor(.primary)
                
                Spacer().frame(height: 8)
                
                HStack {
                    ForEach(0..<itemCount, id: \.self) { puzzleIndex in
                        if puzzleIndex < completedItemCount {
                            Image(systemName: "checkmark.circle")
                                .resizable()
                                .scaledToFill()
                                .foregroundColor(.green)
                                .frame(width: 24, height: 24)
                        } else {
                            Image(systemName: "circle")
                                .resizable()
                                .scaledToFill()
                                .foregroundColor(.primary)
                                .frame(width: 24, height: 24)
                        }
                    }
                }
            }
            
            Spacer()
            
            if isLocked {
                Image(systemName: "lock")
                    .resizable()
                    .scaledToFit()
                    .frame(width: 32, height: 32, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
                    .foregroundColor(.primary)
            }
        }
        .frame(minHeight: 72)
    }
}

struct LevelRow_Previews: PreviewProvider {
    static var previews: some View {
        LevelRow(
            index: 0,
            isLocked: false,
            completedItemCount: 1,
            itemCount: 2
        )
    }
}
