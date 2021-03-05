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
        if uiState.selectedAnswer?.isCorrect ?? false {
            QuizProgressManager().notePuzzleSolved(index: Int32(quizIndex))
            return true
        } else {
            return false
        }
    }
    
    private let numPuzzlesPerRow: Int
    private let isLastQuiz: Bool
    
    init(isNavigationActive: Binding<Bool>, quizIndex: Int = 0) {
        self._isNavigationActive = isNavigationActive
        self.quizIndex = quizIndex
        self._uiState = State(
            initialValue: QuizUiState.Companion().forQuizIndex(index: Int32(quizIndex))
        )
        let quizzesPerRow = Int(LevelManager().PUZZLES_PER_ROW)
        self.numPuzzlesPerRow = quizzesPerRow
        self.isLastQuiz = quizIndex >= Quiz.Companion().all.count - 1 ||
            quizIndex % quizzesPerRow == quizzesPerRow - 1
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
        .navigationBarTitle(
            Text(getLevelDescription()),
            displayMode: .inline
        )
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
    
    private func getLevelDescription() -> String {
        let level = quizIndex / numPuzzlesPerRow
        let data = QuizProgressManager().getLevelPageData()
        let rowData = data.rows[level]
        let totalForLevel = rowData.total
        return Levels.getLevelDescription(
            index: quizIndex,
            total: Int(totalForLevel)
        )
    }
}

struct QuizView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            QuizView(isNavigationActive: .constant(true))
        }
    }
}
