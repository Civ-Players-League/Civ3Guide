import SwiftUI
import shared

struct CombatUnitView: View {
    
    let engagement: Engagement
    let isAttacker: Bool
    let size: CGFloat
    let showStats: Bool
    @State private var showAlt: Bool = false
    
    private var unit: MilitaryUnit {
        isAttacker ? engagement.attacker : engagement.defender
    }
    
    private var isDefender: Bool {
        !isAttacker
    }
    
    private var hasAlt: Bool {
        unit.type.altImage != nil
    }
    
    var body: some View {
        VStack(alignment: .center) {
            Text(isDefender ? MR.strings().defender : MR.strings().attacker)
            
            if hasAlt {
                Button(action: { withAnimation { showAlt = !showAlt } }) {
                    unitImageAndLabel()
                }
            } else {
                unitImageAndLabel()
            }
            
            //Text(unit.rank.label).font(.caption)
            Text("\(unit.health)/\(unit.fullHealth)").font(.caption)
            
            if isDefender {
                VStack {
                    if (unit.isFortified) {
                        Text(MR.strings().fortified).font(.caption)
                    }
                    if engagement.cityDefenceBonus == nil || engagement.terrain == .hills {
                        // Terrain is irrelevant unless the city is on a hill.
                        Text(engagement.terrain.label).font(.caption)
                    }
                    switch engagement.cityDefenceBonus {
                    case 0.0:
                        Text(MR.strings().town).font(.caption)
                    case 0.5:
                        Text(MR.strings().town_walls).font(.caption)
                    case 1.0:
                        Text(MR.strings().metropolis).font(.caption)
                    default:
                        EmptyView()
                    }
                }
            }
            
            if showStats {
                VStack {
                    if isAttacker {
                        Text("Attack: \(unit.type.attack)").font(.caption)
                    } else {
                        Text("Defence: \(unit.type.defence)").font(.caption)
                    }
                    HStack(spacing: 4) {
                        Text("Cost: \(unit.type.cost)").font(.caption)
                        Image(MR.images().shield)
                            .resizable()
                            .frame(width: 14, height: 16)
                    }
                }
            }
            
        }
    }
    
    @ViewBuilder
    private func unitImageAndLabel() -> some View {
        VStack {
            HStack(alignment: .top) {
                if isAttacker {
                    HPView(total: Int(unit.rank.health), damage: Int(unit.damage))
                }
                
                
                Image(showAlt ? unit.type.altImage ?? unit.type.image : unit.type.image)
                    .renderingMode(.original)
                    .resizable()
                    .scaledToFit()
                    .fixedSize()
                    .frame(width: size, height: size * 130 / 119.0)
                    .rotation3DEffect(.degrees(isAttacker ? 0 : 180), axis: (x: 0, y: 1, z: 0))
                
                if isDefender {
                    HPView(total: Int(unit.rank.health), damage: Int(unit.damage))
                }
            }
            
            Text(showAlt ? unit.type.altLabel ?? unit.type.label : unit.type.label)
                .font(.body)
                .foregroundColor(.primary)
                .multilineTextAlignment(.center)
        }
        .frame(maxWidth: .infinity)
    }
}

struct CombatUnitView_Previews: PreviewProvider {
    static var previews: some View {
        CombatUnitView(
            engagement: Engagement(
                attacker: MilitaryUnit(
                    rank: .veteran,
                    type: StandardUnitType.archer,
                    isFortified: false,
                    damage: 0
                ),
                defender: MilitaryUnit(
                    rank: .veteran,
                    type: StandardUnitType.spearman,
                    isFortified: true,
                    damage: 0
                ),
                terrain: .bonusGrassland,
                acrossRiver: true,
                cityDefenceBonus: 0.0
            ),
            isAttacker: false,
            size: CGFloat(100),
            showStats: true
        )
    }
}
