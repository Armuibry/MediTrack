# MediTrack Setup Instructions

## Prerequisites

Before setting up MediTrack, ensure you have the following installed:

1. **Java Development Kit (JDK) 11 or higher**
2. **Maven 3.6 or higher**
3. **A terminal/command prompt**
4. **Text editor or IDE** (IntelliJ IDEA, Eclipse, VS Code recommended)

## Step-by-Step Installation Guide

### Step 1: Install Java Development Kit (JDK)

#### For Windows:

1. Download JDK 11 or higher from:

   - Oracle: https://www.oracle.com/java/technologies/downloads/
   - OpenJDK: https://adoptium.net/

2. Run the installer and follow the installation wizard

3. Set JAVA_HOME environment variable:

   - Open "Environment Variables" (Search in Windows)
   - Add new System Variable:
     - Variable name: `JAVA_HOME`
     - Variable value: `C:\Program Files\Java\jdk-11` (or your JDK path)

4. Add Java to PATH:

   - Edit PATH variable
   - Add: `%JAVA_HOME%\bin`

5. Verify installation:
   ```cmd
   java -version
   javac -version
   ```

#### For macOS:

1. Install using Homebrew:

   ```bash
   brew install openjdk@11
   ```

2. Or download from:

   - Oracle: https://www.oracle.com/java/technologies/downloads/
   - Adoptium: https://adoptium.net/

3. Set JAVA_HOME (add to ~/.zshrc or ~/.bash_profile):

   ```bash
   export JAVA_HOME=$(/usr/libexec/java_home)
   export PATH=$JAVA_HOME/bin:$PATH
   ```

4. Verify installation:
   ```bash
   java -version
   javac -version
   ```

#### For Linux:

1. Install using package manager:

   ```bash
   sudo apt-get update
   sudo apt-get install openjdk-11-jdk
   ```

2. Set JAVA_HOME (add to ~/.bashrc):

   ```bash
   export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
   export PATH=$JAVA_HOME/bin:$PATH
   ```

3. Verify installation:
   ```bash
   java -version
   javac -version
   ```

### Step 2: Install Maven

#### For Windows:

1. Download Maven from: https://maven.apache.org/download.cgi

   - Download `apache-maven-3.x.x-bin.zip`

2. Extract to a directory (e.g., `C:\Program Files\Apache\maven`)

3. Set MAVEN_HOME environment variable:

   - Variable name: `MAVEN_HOME`
   - Variable value: `C:\Program Files\Apache\maven`

4. Add Maven to PATH:

   - Add: `%MAVEN_HOME%\bin`

5. Verify installation:
   ```cmd
   mvn -version
   ```

#### For macOS:

1. Install using Homebrew:

   ```bash
   brew install maven
   ```

2. Verify installation:
   ```bash
   mvn -version
   ```

#### For Linux:

1. Install using package manager:

   ```bash
   sudo apt-get update
   sudo apt-get install maven
   ```

2. Verify installation:
   ```bash
   mvn -version
   ```

### Step 3: Setup MediTrack Project

1. **Navigate to project directory:**

   ```bash
   cd /path/to/MediTrack
   ```

2. **Verify project structure:**

   ```
   MediTrack/
   ├── pom.xml
   ├── src/
   │   └── main/
   │       └── java/
   │           └── com/
   │               └── airtribe/
   │                   └── meditrack/
   └── docs/
   ```

3. **Install dependencies:**

   ```bash
   mvn clean install
   ```

   This will:

   - Download SQLite JDBC driver
   - Compile all Java source files
   - Run tests (if any)
   - Create target directory with compiled classes

4. **Verify compilation:**

   ```bash
   mvn compile
   ```

   You should see:

   ```
   [INFO] BUILD SUCCESS
   ```

### Step 4: Run the Application

#### Option 1: Using Maven Exec Plugin

```bash
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main"
```

#### Option 2: Manual Compilation and Execution

1. Compile the project:

   ```bash
   mvn clean compile
   ```

2. Run the main class:
   ```bash
   java -cp "target/classes:target/dependency/*" com.airtribe.meditrack.Main
   ```

#### Option 3: Using IDE

1. Open the project in your IDE (IntelliJ IDEA, Eclipse, VS Code)
2. Import as Maven project
3. Wait for dependencies to download
4. Run `Main.java` directly from the IDE

### Step 5: Run Tests

Execute the test runner:

```bash
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.test.TestRunner"
```

## Troubleshooting

### Issue: "java: command not found"

**Solution:**

- Verify JAVA_HOME is set correctly
- Restart terminal/command prompt after setting environment variables
- Check PATH includes Java bin directory

### Issue: "mvn: command not found"

**Solution:**

- Verify MAVEN_HOME is set correctly
- Add Maven bin to PATH
- Restart terminal after setting environment variables

### Issue: "Package does not exist" errors

**Solution:**

- Run `mvn clean install` to download dependencies
- Ensure internet connection for Maven to download packages
- Check pom.xml is in the correct location

### Issue: SQLite database errors

**Solution:**

- SQLite database file (`meditrack.db`) will be created automatically on first run
- Ensure write permissions in the project directory
- Database will be initialized automatically when Main.java runs

### Issue: Compilation errors

**Solution:**

- Ensure you're using JDK 11 or higher
- Check that all source files are in correct package structure
- Verify pom.xml configuration is correct

## Verification Checklist

- [ ] Java is installed (`java -version` works)
- [ ] JDK is installed (`javac -version` works)
- [ ] Maven is installed (`mvn -version` works)
- [ ] Project compiles without errors (`mvn compile`)
- [ ] Application runs successfully
- [ ] Database file is created (`meditrack.db` appears)
- [ ] Test runner executes successfully

## Next Steps

After successful setup:

1. Run the application and explore the menu
2. Create sample patients and doctors
3. Create appointments
4. Test search functionality
5. Generate bills
6. Run analytics

## Additional Resources

- Java Documentation: https://docs.oracle.com/javase/11/
- Maven Documentation: https://maven.apache.org/guides/
- SQLite Documentation: https://www.sqlite.org/docs.html

---

**Note:** Screenshots of installation steps should be added here for visual reference.
