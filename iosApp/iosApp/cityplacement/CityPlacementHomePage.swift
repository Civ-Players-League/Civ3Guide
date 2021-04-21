import SwiftUI
import shared

struct CityPlacementHomePage: View {
    
    @State private var data = CityPlacementProgressManager().getLevelPageData()
    
    var body: some View {
        LevelList(data, header: MR.strings().city_placement_intro) { launchIndex, isNavigationActive in
            CityPlacementView(
                puzzleIndex: Int(launchIndex),
                isNavigationActive: isNavigationActive
            )
        }
        .navigationBarTitle(
            Text(MR.strings().city_placement_title),
            displayMode: .inline
        )
        .onAppear {
            withAnimation {
                data = CityPlacementProgressManager().getLevelPageData()
            }
        }
    }
}

struct CityPlacementHomePage_Previews: PreviewProvider {
    static var previews: some View {
        CityPlacementHomePage()
    }
}
