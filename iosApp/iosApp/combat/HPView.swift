import SwiftUI

struct HPView: View {
    
    let total: Int
    let damage: Int
    
    var healthColor: Color {
        if damage == total - 1 {
            return .red
        } else if damage == 0 {
            return .green
        } else if damage == 1 && total >= 4 {
            return .green
        } else {
            return .yellow
        }
    }
    
    var body: some View {
        VStack(spacing: 2) {
            ForEach(0..<total, id: \.self) { i in
                Spacer()
                    .frame(width: 4, height: 20)
                    .fixedSize()
                    .background(i < damage ? .black : healthColor)
            }
        }
        .background(Color.black)
        .overlay(
                RoundedRectangle(cornerRadius: 4)
                    .stroke(Color.white, lineWidth: 1)
            )
    }
}


struct HPView_Previews: PreviewProvider {
    static var previews: some View {
        HPView(total: 5, damage: 0)
    }
}
