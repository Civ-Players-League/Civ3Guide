import SwiftUI
import shared

struct HelpPage: View {
    
    let title: ResourcesStringResource
    let message: ResourcesStringResource
    
    var body: some View {
        VStack {
            Text(title).font(/*@START_MENU_TOKEN@*/.title/*@END_MENU_TOKEN@*/)
            Spacer().frame(height: 16)
            Text(message)
        }.padding()
    }
}

struct HelpPage_Previews: PreviewProvider {
    static var previews: some View {
        HelpPage(
            title: MR.strings().home_label_worker_action_title,
            message: MR.strings().worker_puzzle_help_text
        )
    }
}
