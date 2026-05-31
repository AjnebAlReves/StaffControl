#!/bin/bash
# Installs legacy dependencies into local .m2 for StaffPlusCore to compile against.
#
# Step 1: Download & install craftbukkit jars (needed by legacy version modules)
# Step 2: Install StaffPlusAPI into .m2
# Step 3: Compile & install legacy version modules (v1_7_R1 .. v1_16_R2)

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

# -------------------------------------------------------------------
# Step 1: CraftBukkit jars
# -------------------------------------------------------------------
declare -a VERSIONS=(
  1.7.2-R0.3-SNAPSHOT
  1.7.5-R0.1-SNAPSHOT
  1.7.8-R0.1-SNAPSHOT
  1.7.10-R0.1-SNAPSHOT
  1.8-R0.1-SNAPSHOT
  1.8.3-R0.1-SNAPSHOT
  1.8.8-R0.1-SNAPSHOT
  1.9.2-R0.1-SNAPSHOT
  1.9.4-R0.1-SNAPSHOT
  1.10.2-R0.1-SNAPSHOT
  1.11.2-R0.1-SNAPSHOT
  1.12-R0.1-SNAPSHOT
  1.13-R0.1-SNAPSHOT
  1.13.2-R0.1-SNAPSHOT
  1.14-R0.1-SNAPSHOT
  1.14.4-R0.1-SNAPSHOT
  1.15-R0.1-SNAPSHOT
  1.15.1-R0.1-SNAPSHOT
)

mkdir -p "$SCRIPT_DIR/lib"
cd "$SCRIPT_DIR/lib"

for i in "${VERSIONS[@]}"; do
  FILE="craftbukkit-$i.jar"
  if [[ ! -f "$FILE" ]]; then
    echo "Downloading $FILE ..."
    # Attempt download from various sources; skip if all fail
    ORIGIN="https://cdn.getbukkit.org/craftbukkit/craftbukkit-$i.jar"
    FALLBACK="https://repo.codemc.org/repository/craftbukkit/org/bukkit/craftbukkit/${i}/craftbukkit-${i}.jar"
    curl -fsSL --connect-timeout 10 "$ORIGIN" -o "$FILE" 2>/dev/null \
      || curl -fsSL --connect-timeout 10 "$FALLBACK" -o "$FILE" 2>/dev/null \
      || echo "  [WARN] Could not download $FILE. Place it manually in lib/ and re-run."
  fi

  if [[ -f "$FILE" ]]; then
    mvn install:install-file \
      -Dfile="$FILE" \
      -Dpackaging=jar \
      -DgeneratePom=true \
      -DgroupId=org.bukkit \
      -DartifactId=craftbukkit \
      -Dversion="$i"
    echo "  Installed craftbukkit-$i to local .m2"
    rm "$FILE"
  fi
done

cd "$SCRIPT_DIR"

# -------------------------------------------------------------------
# Step 2: Install StaffPlusAPI (needed by legacy version modules)
# -------------------------------------------------------------------
echo "Installing StaffPlusAPI into local .m2 ..."
cd "$SCRIPT_DIR/StaffPlusAPI"
mvn clean install -DskipTests -q

cd "$SCRIPT_DIR"

# -------------------------------------------------------------------
# Step 3: Build and install legacy version modules
# -------------------------------------------------------------------
LEGACY_MODULES=(
  v1_7_R1 v1_7_R2 v1_7_R3 v1_7_R4
  v1_8_R1 v1_8_R2 v1_8_R3
  v1_9_R1 v1_9_R2
  v1_10_R1
  v1_11_R1
  v1_12_R1
  v1_13_R1 v1_13_R2
  v1_14_R1 v1_14_R2
  v1_15_R1
  v1_16_R1 v1_16_R2
)

echo "Building and installing legacy version modules ..."
for MOD in "${LEGACY_MODULES[@]}"; do
  if [[ -d "$SCRIPT_DIR/$MOD" ]]; then
    echo "  Installing $MOD ..."
    cd "$SCRIPT_DIR/$MOD"
    mvn clean install -DskipTests -q
  fi
done

cd "$SCRIPT_DIR"
echo ""
echo "Done. Legacy dependencies are installed in local .m2."
echo "Now run 'mvn clean package' to build StaffPlusCore."
