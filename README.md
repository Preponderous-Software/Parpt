# Parpt - Project Audit & Revenue Prioritization Tool
Parpt is an interactive CLI tool that helps developers ,indie creators and teams evaluate and prioritize their software projects using structured metrics like ICE and RICE.

## Features
- Guided project scoring using ICE and RICE methods
- Saves project entries to Markdown and JSON
- Obsidian-compatible for review workflows
- Ranka nd sort projects by monetization, potential, feasability and effort
- Modular Spring Boot architecture
- 100% local-first and open source

## Prioritization Frameworks
- ICE (Impact, Confidence, Effort)
- RICE (Reach, Impact, Confidence, Effort)

Parpt guides you step-by-step through scoring each project and calculating these metrics, then exports the data for future review.

## Installation
Clone and build manually:
```bash
git clone https://www.github.com/Preponderous/Parpt.git
cd Parpt
./gradlew build
java -jar build/libs/parpt.jar
```

## Getting Started
Run the CLI:
```bash
java -jar parpt.jar
```

You'll be prompted to enter:
- Project name and description
- Key factors (impact, confidence, reach, effort, etc)
- Parpt will then:
  - Calculate ICE and RICE scores
  - Save the resulst to `projects.json` and `projects.md`
  - Help you sort and review your efforts over time

## Roadmap
- [ ] Project input and validation loop
- [ ] Score calculation engine
- [ ] Markdown and JSON writer modules
- [ ] CLI configuration and persistence

## Contributing
This project is in early development. Contributions, suggestions and issue reports are welcome!

- Open an issue
- Fork and submit a PR
- Join the discussion

## License
MIT License

Copyright (c) 2025 Daniel McCoy Stephenson / Preponderous Software

You are free to use, modify and distribute this software under the terms of the MIT License. See LICENSE for details.

## About Preponderous
Preponderous Software builds developer tools, simulations and creative systems taht help people focus their energy where it counts most.

Visit us at: [https://www.preponderous.org](https://www.preponderous.org)
