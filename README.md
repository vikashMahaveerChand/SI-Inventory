# SI-Inventory

Inventory management project.

## Getting Started

### Build the Windows executable

On Windows, run:

```powershell
.\gradlew.bat createExe
```

The executable is created at `build\launch4j\SI-Inventory.exe`. Distribute the
contents of `build\launch4j` to client machines (including a `lib` folder if
one is generated). Java 8 or later must be installed on each client machine.
The executable uses the SI-Inventory icon from
`src\main\resources\icons\si-inventory.ico`.

### Configure MySQL

On the first launch, the application asks for the MySQL host, port, database
name, username, and password. It tests the connection and saves the settings
to `%APPDATA%\SI-Inventory\database.properties` only after a successful
connection (typically
`C:\Users\<username>\AppData\Roaming\SI-Inventory\database.properties`).
On later launches it reuses the saved settings; if the connection fails, you
can enter updated settings. The file contains the database password, so keep
your Windows user account protected.

Settings previously saved in the application's working-directory `config`
folder are not migrated automatically; enter them again when prompted.

The application does not yet read or write business data. On startup,
Liquibase applies pending database changes from
`src/main/resources/database/db.changelog-master.xml` and records applied
changes in Liquibase's `DATABASECHANGELOG` table. Add future schema changes as
Liquibase changesets in this resource changelog; do not apply schema SQL outside
Liquibase. An external MySQL server is recommended for a workload with
heavy reads and writes, especially when multiple application users need
shared data and centralized backups. An embedded database is better suited
to a single-user, local-only application.

## License

TBD
