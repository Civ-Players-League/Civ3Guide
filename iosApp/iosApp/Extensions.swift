import Foundation
import SwiftUI
import shared

extension View {
    @inline(__always) func also<T: View>(block: (Self) -> T) -> T {
        return block(self)
    }
    
    @ViewBuilder func hidden(_ hidden: Bool, remove: Bool = false) -> some View {
        if hidden {
            if !remove {
                self.hidden()
            }
        } else {
            self
        }
    }
    
    @ViewBuilder func mapOrElse<T, V1: View, V2: View>(_ value: T?, content: (T) -> V1, fallback: () -> V2) -> some View {
        if value == nil {
            fallback()
        } else {
            content(value!)
        }
    }
    
    func navigationBarTitle(_ title: ResourcesStringResource, displayMode: NavigationBarItem.TitleDisplayMode) -> some View {
        navigationBarTitle(Text(title.load()), displayMode: displayMode)
    }
}

extension Point {
    func toCGPoint(scaleFactor: Float) -> CGPoint {
        CGPoint(x : CGFloat(x * scaleFactor), y: CGFloat(y * scaleFactor))
    }
}

extension Tile {
    func getLabel() -> String {
        var result = "\(terrain.label.load())"
        if (resource != nil) {
            result += " + \(resource!.name)"
        }
        return result
    }
}

extension Text {
    init(_ resource: ResourcesStringResource) {
        self.init(resource.load())
    }
    
    init(_ resource: ResourcesStringResource, _ arg1: Int) {
        self.init(resource.format(int: Int32(arg1)))
    }
    
    init(_ resource: ResourcesStringResource, _ string: String) {
        self.init(resource.format(string: string))
    }
}

extension Image {
    init(_ resource: ResourcesImageResource) {
        self.init(uiImage: resource.toUIImage()!)
    }
}

