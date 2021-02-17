import SwiftUI

// Acts like a VStack or HStack depending on isHorizontal.
struct VHStack<Content>: View where Content : View {
    
    let orientation: Orientation
    let content: () -> Content
    
    init(orientation: Orientation, @ViewBuilder content: @escaping () ->  Content) {
        self.orientation = orientation
        self.content = content
    }
    
    var body: some View {
        if orientation == .vertical {
            VStack {
                content()
            }
        } else {
            HStack {
                content()
            }
        }
    }
}

enum Orientation {
    case vertical
    case horizontal
}

struct VHStack_Previews: PreviewProvider {
    static var previews: some View {
        VHStack(orientation: .vertical) {
            
        }
    }
}
