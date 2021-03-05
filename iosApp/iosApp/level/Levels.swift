import shared

struct Levels {
    static func getLevelDescription(index: Int, total: Int) -> String {
        let numPuzzlesPerRow = Int(LevelManager().PUZZLES_PER_ROW)
        let level = index / numPuzzlesPerRow
        let levelIndex = index % numPuzzlesPerRow
        return MR.strings().level_s.format(
            string: "\(level + 1), \(levelIndex + 1)/\(total)"
        )
    }
}


