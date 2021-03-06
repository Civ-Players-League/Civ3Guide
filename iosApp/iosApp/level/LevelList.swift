import SwiftUI
import shared

struct LevelList<Destination>: View where Destination : View {
    
    let header: ResourcesStringResource
    let data: LevelPageData
    let destination: (Int, Binding<Bool>) -> Destination
    
    @State private var activeLevelIndex: Int? = nil
    
    init(
        _ data: LevelPageData,
        header: ResourcesStringResource,
        @ViewBuilder destination: @escaping (Int, Binding<Bool>) -> Destination) {
        self.data = data
        self.header = header
        self.destination = destination
    }
    
    
    var body: some View {
        ZStack {
            ForEach(0..<data.rows.count, id: \.self) { rowIndex in
                NavigationLink(
                    destination: destination(
                        Int(data.rows[rowIndex].launchIndex),
                        Binding(
                            get: { activeLevelIndex == rowIndex },
                            set: { isActive in
                                activeLevelIndex = isActive ? rowIndex : nil
                            }
                        )
                    ),
                    isActive: Binding(
                        get: { activeLevelIndex == rowIndex },
                        set: { isActive in
                            activeLevelIndex = isActive ? rowIndex : nil
                        }
                    )
                ) {
                    EmptyView()
                }
                .isDetailLink(false)
            }
        }
        List {
            VStack {
                Text(header)
                
                Spacer().frame(height: 8)
            }
            
            ForEach(0..<data.rows.count, id: \.self) { rowIndex in
                Button(action: { activeLevelIndex = rowIndex }) {
                    createRow(rowIndex, data.rows[rowIndex])
                }
                .disabled(data.rows[rowIndex].isLocked)
            }
        }
    }
    
    @ViewBuilder
    private func createRow(_ index: Int, _ row: LevelPageRowData) -> some View {
        LevelRow(
            index: index,
            isLocked: row.isLocked,
            completedItemCount: Int(row.completed),
            itemCount: Int(row.total)
        )
    }
}

struct LevelList_Previews: PreviewProvider {
    static var previews: some View {
        LevelList(
            LevelPageData(rows: []),
            header: MR.strings().app_description
        ) { index, isNavigationActive in
            EmptyView()
        }
    }
}
