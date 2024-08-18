# Script created by aclij
# GitHub https://github.com/aclijpio

mvn clean install

project_build_fileName=$(find target -type f -name '*.jar' | grep -v '/$')
if [ -z "$project_build_fileName" ]; then
  echo "File not found."
  exit 1
fi

echo "The file found."

docker build --build-arg JAR_FILE="$project_build_fileName" -t animalcare -f Dockerfile .

echo "Docker image built successfully."