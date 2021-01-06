import SwiftUI
import shared

struct MapView: View {
    let map: MapConfiguration
    let touchDelegate: MapTouchDelegate
    let geo: GeometryProxy
    @Binding var selectedTile: TileInfo?
    
    init(map: MapConfiguration, geo: GeometryProxy, selectedTile: Binding<TileInfo?>) {
        self.map = map
        self.geo = geo
        self.touchDelegate = MapTouchDelegate(map: map)
        self._selectedTile = selectedTile
    }
    
    var body: some View {
            ZStack {
                Image(map.image)
                    .resizable()
                    .scaledToFit()
                    .gesture(
                        DragGesture(minimumDistance: 0)
                            .onChanged { value in
                                touchDelegate.onTouch(point: adjustPoint(value.location))
                            }.onEnded { value in
                                let newSelectedTile =
                                    touchDelegate.onTouchEnd(point: adjustPoint(value.location))
                                withAnimation {
                                    if newSelectedTile?.isEqual(selectedTile) == true {
                                        selectedTile = nil
                                    } else {
                                        selectedTile = newSelectedTile
                                    }
                                }
                            }
                    )
                selectedTile.map { tile in
                    Path { path in
                        let scaleFactor = getScaleFactor()
                        let bounds = map.getBounds(tile: tile)
                        let leftPoint = bounds.left.toCGPoint(scaleFactor: scaleFactor)
                        let topPoint = bounds.top.toCGPoint(scaleFactor: scaleFactor)
                        let rightPoint = bounds.right.toCGPoint(scaleFactor: scaleFactor)
                        let bottomPoint = bounds.bottom.toCGPoint(scaleFactor: scaleFactor)
                        path.move(to: leftPoint)
                        path.addLine(to: topPoint)
                        path.addLine(to: rightPoint)
                        path.addLine(to: bottomPoint)
                        path.addLine(to: leftPoint)
                    }
                    .fill(Color.blue)
                    .opacity(0.6)
                    .allowsHitTesting(/*@START_MENU_TOKEN@*/false/*@END_MENU_TOKEN@*/)
                }
            }.frame(
                width: CGFloat(Float(map.width) * getScaleFactor()),
                height: CGFloat(Float(map.height) * getScaleFactor()),
                alignment: .center)
            .cornerRadius(/*@START_MENU_TOKEN@*/3.0/*@END_MENU_TOKEN@*/)
            .overlay(Rectangle().stroke(Color.black, lineWidth: 1).cornerRadius(/*@START_MENU_TOKEN@*/3.0/*@END_MENU_TOKEN@*/))
    }
    
    private func getScaleFactor() -> Float {
        let size = geo.size
        let widthRatio = Float(size.width) / Float(map.width)
        let heightRatio = Float(size.height) / Float(map.height)
        return min(widthRatio, heightRatio)
    }
    
    private func adjustPoint(_ point: CGPoint) -> Point {
        let scaleRatio = getScaleFactor()
        let scaledX = Float(point.x) / scaleRatio
        let scaledY = Float(point.y) / scaleRatio
        return Point(x: scaledX, y_: scaledY)
    }
}

struct MapView_Previews: PreviewProvider {
    static var previews: some View {
        GeometryReader { geo in
            MapView(map: MapConfigurations().all.first!, geo: geo, selectedTile: .constant(nil))
        }
    }
}
