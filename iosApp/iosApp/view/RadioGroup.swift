import SwiftUI

struct RadioGroup: View {
    
    let items: [String]
    
    @Binding var selectedIndex: Int?
    
    let horizontalAlignment: HorizontalAlignment
    let verticalAlignment: VerticalAlignment
    let isHorizontal: Bool
    let spacing: CGFloat?
    
    init(
        items: [String],
        selectedIndex: Binding<Int?>,
        horizontalAlignment: HorizontalAlignment = .leading,
        verticalAlignment: VerticalAlignment = .center,
        isHorizontal: Bool = false,
        spacing: CGFloat? = 16
    ) {
        self.items = items
        self._selectedIndex = selectedIndex
        self.horizontalAlignment = horizontalAlignment
        self.verticalAlignment = verticalAlignment
        self.isHorizontal = isHorizontal
        self.spacing = spacing
    }
    
    var body: some View {
        if (isHorizontal) {
            HStack(alignment: verticalAlignment, spacing: spacing) {
                createInterior()
            }.fixedSize(horizontal: true, vertical: false)
        } else {
            VStack(alignment: horizontalAlignment, spacing: spacing) {
                createInterior()
            }.fixedSize(horizontal: false, vertical: true)
        }
        
    }
    
    @ViewBuilder
    private func createInterior() -> some View {
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
                ),
                isHorizontal: true
            )
        }
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
