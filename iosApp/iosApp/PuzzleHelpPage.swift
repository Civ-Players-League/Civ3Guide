import SwiftUI
import shared

struct PuzzleHelpPage: View {
    var body: some View {
        VStack {
            Text(MR.strings().home_label_worker_action_title).font(/*@START_MENU_TOKEN@*/.title/*@END_MENU_TOKEN@*/)
            Spacer().frame(height: 8)
            Text(MR.strings().worker_puzzle_help_text)
        }.padding()
    }
}

struct PuzzleHelpPage_Previews: PreviewProvider {
    static var previews: some View {
        PuzzleHelpPage()
    }
}
