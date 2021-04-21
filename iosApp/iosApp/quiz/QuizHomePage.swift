import SwiftUI
import shared

struct QuizHomePage: View {
    
    @State private var data = QuizProgressManager().getLevelPageData()
    
    var body: some View {
        LevelList(data, header: MR.strings().quiz_intro) { launchIndex, isNavigationActive in
            QuizView(
                isNavigationActive: isNavigationActive,
                quizIndex: Int(launchIndex)
            )
        }
        .navigationBarTitle(
            Text(MR.strings().home_label_quiz_title),
            displayMode: .inline
        )
        .onAppear {
            withAnimation {
                data = QuizProgressManager().getLevelPageData()
            }
        }
    }
}

struct QuizHomePage_Previews: PreviewProvider {
    static var previews: some View {
        QuizHomePage()
    }
}
