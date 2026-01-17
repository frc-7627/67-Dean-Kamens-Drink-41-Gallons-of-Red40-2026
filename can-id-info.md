# ID Table
Click [here](can-id-table.xlsx) to quickly look up a CAN ID. It should be updated according to the [following section](#computing-can-ids).


# Computing CAN IDs
This should be the single source of truth for computing CAN IDs. If you need to quickly find an id, look [here](#id-table)

## Main offsets
- `id(section, info...): start offset + section offset(section) + info offset(section, info...)`
- `start offset: 1`
- `section count(section)` when `section =`
   - `GYRO: 1`
   - `SWERVE: x total * y total * role total`
   - `PROTOTYPE: 1`
- `section offset(section)` when `section =`
   - `GYRO: 0`
   - `SWERVE: section offset(GYRO) + section count(GYRO)`
   - `PROTOTYPE: section offset(SWERVE) + section count(SWERVE)`
- `info offset(section, info...)` when `section, info... =`
   - `GYRO: 0`
   - `SWERVE, x side, y side, role: x offset(x side) * y total * role total + y offset(y offset) * role total + role offset(role)` 
   - `PROTOTYPE: 0`

## Swerve offsets
- `x total: 2`
- `y total: 2`
- `role total: 3`
- `x offset(x side)` when `x side =` 
   - `LEFT: 0`
   - `RIGHT: 1`
- `y offset(y side)` when `y side =`
   - `BACK: 0`
   - `FRONT: 1`
- `role offset` when `role =`
   - `DRIVE: 0`
   - `ANGLE: 1`
   - `ENCODER: 2`