import shared

extension Optional where Wrapped == Int {
    func toKotlin() -> KotlinInt? {
        return self.map { value in KotlinInt(integerLiteral: value) }
    }
}
