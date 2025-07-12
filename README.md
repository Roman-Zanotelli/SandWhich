# 🥪 SandWhich (Archived)

**SandWhich?** *The Sandwich Shop.*  
A playful, project showcasing **Java build annotations** and **code generation**

This project was created in 5-7 days with no prior planning or knowledge, the goal was just to "build a sandwhich shop CLI" but i decided to take the intitave to extend it beyond that.

This project automatically scans imports and generates code including range-bounded switch cases, user/menu interaction loops, and a semi-flushed out Annotation system to generate your own menus

---

## 📦 Project Overview

**SandWhich** is a proof-of-concept application that models a fictional sandwich shop. More importantly, it demonstrates the use of:

- ✅ **Custom Java Annotations**
- 🛠️ **Annotation Processors**
- ⚙️ **Compile-time Code Generation**
- 📚 **Clean Java design principles**

While the business logic is intentionally simple (ordering sandwiches and managing options), the core goal was to explore advanced Java build-time metaprogramming techniques.

---

## 🧠 Technologies Used

- Java 17+
- Annotation Processing (APT)
- Maven
- Meta-model APIs

---

## 💡 What This Project Demonstrates

- How to define and use custom annotations
- Generating boilerplate classes and methods automatically
- Structuring a modular Java project using code generation
- Applying Java’s compile-time tooling to fully functioning project

## ScreenShots
### Project Code w/ Annotations:

<img width="1377" height="980" alt="ExampleOrderMenu" src="https://github.com/user-attachments/assets/81beb764-22d9-4870-b701-4ec63c795880" />

### Example @OnLoad Usage:

<img width="712" height="270" alt="ExampleOnLoad" src="https://github.com/user-attachments/assets/511adc33-94d5-4461-9503-fea236648db0" />

### Example @OnSelect Usage:

<img width="649" height="361" alt="OnOptionSelectExample" src="https://github.com/user-attachments/assets/40831066-eff0-4828-973c-abae57bd8ff8" />


### Generated Code (2 part):

<img width="699" height="942" alt="ExampleGeneratedOrderMenu" src="https://github.com/user-attachments/assets/7ea93ad1-f2ee-4ca8-8392-11ef645cb266" />
<img width="710" height="441" alt="GeneratedOnSelectExample" src="https://github.com/user-attachments/assets/4471f1de-4419-4c15-b86e-876000245210" />


### Menu Generator (snippet)
<img width="808" height="939" alt="MenuGenerator" src="https://github.com/user-attachments/assets/a41006a1-fa59-4e86-8c10-6a18058b0f20" />

**read more code inside project under expresso-processor sub-module**

# 🤖 AI Assistance Acknowledgment

Parts of this README were created with assistance from [ChatGPT by OpenAI](https://openai.com/chatgpt) to help organize ideas and improve clarity

> ⚖️ **Ethical Use Statement**: I believe in responsible and transparent use of AI tools. While AI helped shape structure and language of the readme, all architectural decisions, implementation, and final content reflect my own understanding, judgment, and intent.


