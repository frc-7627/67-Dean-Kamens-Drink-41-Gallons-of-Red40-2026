# ID Table
Click [here](can-id-table.xlsx) to quickly look up a CAN ID. It should be updated according to the [following section](#computing-can-ids).


# Computing CAN IDs
This should be the single source of truth for computing CAN IDs. If you need to quickly find an id, look [here](#id-table)

- `id(section, info...): start offset + section offset(section) + info offset(section, info...)`
- `start offset: 1`
- `section count(section)` when `section =`
   - `gyro: 1`
   - `swerve: x total * y total * role total`
   - `prototype: 1`
- `section offset(section)` when `section =`
   - `gyro: 0`
   - `swerve: section offset(gyro) + section count(gyro)`
   - `prototype: section offset(swerve) + section count(swerve)`
- `info offset(section, info...)`

- `x total: 2`
- `y total: 2`
- `role total: 3`
- `x offset(x side)` when `x side =` 
   - `left: 0`
   - `right: 1`
- `y offset(y side)` when `y side =`
   - `left: 0`
   - `right: 1`
- `role offset` when `role =`
   - `drive: 0`
   - `angle: 1`
   - `encoder: 2`