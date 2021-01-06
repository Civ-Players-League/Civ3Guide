import SwiftUI

struct RadioButton: View {
    
    let text: String
    
    @Binding var isChecked: Bool
    
    var body: some View {
        Button(action: onClick) {
            HStack {
                Image(systemName: isChecked ? "largecircle.fill.circle" : "circle")
                    .resizable()
                    .padding(4)
                    .frame(width: 24, height: 24)
                    .foregroundColor(.accentColor)
                Text(text)
            }.foregroundColor(.primary)
        }
    }
    
    private func onClick() {
        isChecked = !isChecked
    }
}

struct RadioButton_Previews: PreviewProvider {
        
    static var previews: some View {
        RadioButton(text: "Foo\nBar", isChecked: .constant(false))
    }
}
