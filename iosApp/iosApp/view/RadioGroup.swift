import SwiftUI

struct RadioGroup: View {
    
    let items: [String]
    
    @Binding var selectedIndex: Int?
    
    let alignment: HorizontalAlignment
    let spacing: CGFloat?
    
    init(
        items: [String],
        selectedIndex: Binding<Int?>,
        alignment: HorizontalAlignment = .leading,
        spacing: CGFloat? = nil
    ) {
        self.items = items
        self._selectedIndex = selectedIndex
        self.alignment = alignment
        self.spacing = spacing
    }
    
    var body: some View {
        VStack(alignment: alignment, spacing: spacing) {
            ForEach(0..<items.count) { index in
                RadioButton(
                    text: items[index],
                    isChecked: Binding(
                        get: { selectedIndex == index },
                        set: { checked in
                            if selectedIndex == index {
                                selectedIndex = nil
                            } else {
                                selectedIndex = index
                            }
                        }
                    )
                )
            }
        }.fixedSize(horizontal: false, vertical: true)
    }
}

struct RadioGroup_Previews: PreviewProvider {
    static var previews: some View {
        RadioGroup(
            items: ["Hello", "World"],
            selectedIndex: .constant(0),
            spacing: 16
        )
    }
}
