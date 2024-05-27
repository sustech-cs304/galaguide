# GalaGuide

![logo](./galaguide/src/assets/logo.png)

*GalaGuide* is an MIT-licensed open-source cross-platform software application as your personal manager for campus events & reservations based on Electron, Vue, and Ktor.

GitHub: [![GitHub](https://img.shields.io/badge/GitHub-GalaGuide-blue?logo=github)](https://github.com/sustech-cs304/team-project-24spring-23333)

## Getting Started

The GitHub repository provides both backend and frontend code. The backend is based on Ktor, and the frontend is based on Vue. You could also find the pre-built executable files in the release page. The following instructions will help you to build the project from source code. So if you only want to run the application using the executable file, you can skip the following steps.

## Using pre-built executable files

TODO

## Building from source code

### Prerequisites

- Node.js

TODO: Add more prerequisites

### Installation

1. Clone the repository

```bash
git clone https://github.com/sustech-cs304/team-project-24spring-10.git
```

2. Enter the directory `galaguide/` and install the dependencies for the frontend

```bash
npm install
```

3. Build the backend

TODO

4. Install the requirements for the backend for AI

```bash
cd ../ai
pip install -r requirements.txt
```

### Usage

1. Run the backend

```bash
TODO
cd ../ai
python app.py
```

2. Enter the directory `galaguide/` and run the frontend

```bash
npm run electron:serve
```

After running the frontend, you should see the application window.

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Acknowledgements

GalaGuide is a team project for the course CS304 Software Engineering at Southern University of Science and Technology. The team members are (in ascending order of Pinyin):

- [Hanran Zhou](https://github.com/NoMathExpectation)
- [Haodong He](https://github.com/qwurd231)
- [Jianzheng Huang](https://github.com/Cara-Zinc)
- [Lianji Bai](https://github.com/WinwayCome)
- [Qiwen Xiao](https://github.com/Charley-xiao)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
