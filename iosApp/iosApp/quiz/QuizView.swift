import SwiftUI
import shared

struct QuizView: View {
    
    @Binding var isNavigationActive: Bool
    
    @State private var uiState: QuizUiState
    @State private var goToNext: Bool = false
    
    let quizIndex: Int
    
    var quiz: Quiz {
        uiState.quiz
    }
    
    var isSolved: Bool {
        uiState.selectedAnswer?.isCorrect ?? false
    }
    
    private let isLastQuiz: Bool
    
    init(isNavigationActive: Binding<Bool>, quizIndex: Int = 0) {
        self._isNavigationActive = isNavigationActive
        self.quizIndex = quizIndex
        self._uiState = State(
            initialValue: QuizUiState.Companion().forQuizIndex(index: Int32(quizIndex))
        )
        self.isLastQuiz = quizIndex >= Quiz.Companion().all.count - 1
    }
    
    var body: some View {
        ScrollView {
            VStack(alignment: .center) {
                Text(quiz.question)
                    .multilineTextAlignment(.center)
                    .font(.headline)
                Image(quiz.image)
                    .resizable()
                    .scaledToFit()
                
                Spacer().frame(height: 8)
                
                RadioGroup(
                    items: quiz.answers.map { answer in answer.text },
                    selectedIndex: Binding(
                        get: { uiState.selectedIndex?.intValue },
                        set: { index in
                            uiState = uiState.withSelectedAnswer(index: index.toKotlin())
                        }
                    ),
                    spacing: 16
                )
                
                Spacer().frame(height: 8)
                
                Text(uiState.getExplanationText() ?? "")
                    .foregroundColor(uiState.selectedAnswer?.isCorrect ?? false ? .green : .red)
                
                NavigationLink(
                    destination: getNextDestination(),
                    isActive: $goToNext,
                    label: { EmptyView() }
                ).isDetailLink(false)
            }.padding()
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .navigationBarTitle(Text(MR.strings().puzzle_index, quizIndex + 1), displayMode: .inline)
        .navigationBarItems(
            trailing: Button(
                (isLastQuiz ? MR.strings().done : MR.strings().next).load()
            ) {
                if isLastQuiz {
                    isNavigationActive = false
                } else {
                    goToNext = true
                }
            }
            .disabled(!isSolved)
        )
    }
    
    @ViewBuilder
    private func getNextDestination() -> some View {
        if isLastQuiz {
            EmptyView()
        } else {
            QuizView(
                isNavigationActive: $isNavigationActive,
                quizIndex: quizIndex + 1
            )
        }
    }
}

struct QuizView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            QuizView(isNavigationActive: .constant(true))
        }
    }
}
