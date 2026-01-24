# ID Table
Look below to quickly look up a CAN ID. It should be updated according to the [following section](#computing-can-ids).
| ID | Set? | Section   | X Side | Y Side | Role    |
|----|------|-----------|--------|--------|---------|
| 1  | TRUE | Gyro      |        |        |         |
| 2  | TRUE | Swerve    | Left   | Back   | Drive   |
| 3  | TRUE | Swerve    | Left   | Back   | Angle   |
| 4  | TRUE | Swerve    | Left   | Back   | Encoder |
| 5  | TRUE | Swerve    | Left   | Front  | Drive   |
| 6  | TRUE | Swerve    | Left   | Front  | Angle   |
| 7  | TRUE | Swerve    | Left   | Front  | Encoder |
| 8  | TRUE | Swerve    | Right  | Back   | Drive   |
| 9  | TRUE | Swerve    | Right  | Back   | Angle   |
| 10 | TRUE | Swerve    | Right  | Back   | Encoder |
| 11 | TRUE | Swerve    | Right  | Front  | Drive   |
| 12 | TRUE | Swerve    | Right  | Front  | Angle   |
| 13 | TRUE | Swerve    | Right  | Front  | Encoder |
| 14 | TRUE | Prototype |        |        |         |
| 15 | TRUE | Candle    |        |        |         |
| 16 | TRUE | Launcher  |        |        |         |
| 17 | TRUE | Launcher 2|        |        |         |
|


# Computing CAN IDs
This should be the single source of truth for computing CAN IDs. If you need to quickly find an id, look [here](#id-table)

## Main Offsets
- `id(section, info...)`
   - `start offset + section offset(section) + info offset(section, info...)`
- `start offset`
   - `1`
- `section count(section)` when `section =`
   - `GYRO`
      - `0`
   - `SWERVE`
      - `x total * y total * role total`
   - `PROTOTYPE`
      - `1`
   - `CANDLE`
      - `1`
- `section offset(section)` when `section =`
   - `GYRO`
      - `0`
   - `SWERVE`
      - `section offset(GYRO) + section count(GYRO)`
   - `PROTOTYPE`
      - `section offset(SWERVE) + section count(SWERVE)`
   - `CANDLE`
      - `section offset(PROTOTYPE) + section count(PROTOTYPE)`
- `info offset(section, info...)` when `section, info... =`
   - `GYRO`
      - `0`
   - `SWERVE, x side, y side, role`
      - `x offset(x side) * y total * role total + y offset(y offset) * role total + role offset(role)` 
   - `PROTOTYPE`
      - `0`
   - `CANDLE`
      - `0`

## Swerve Offsets
- `x total`
   - `2`
- `y total`
   - `2`
- `role total`
   - `3`
- `x offset(x side)` when `x side =` 
   - `LEFT`
      - `0`
   - `RIGHT`
      - `1`
- `y offset(y side)` when `y side =`
   - `BACK`
      - `0`
   - `FRONT`
      - `1`
- `role offset` when `role =`
   - `DRIVE`
      - `0`
   - `ANGLE`
      - `1`
   - `ENCODER`
      - `2`