# ai-llm-comparator

This application allows users to input a shared prompt and compare responses from three AI models:

Anthropic Claude (via API key)

DeepSeek R1 Distill Qwen 7B (via LM Studio)

LLaMA 3.2 1B Instruct (via LM Studio)

The project is divided into:

A ReactJS frontend that captures user input, sends requests to the backend, and displays formatted responses.

A Spring Boot backend that routes each prompt to the correct model API and returns a formatted result.

Frontend (ReactJS)
Features
Shared prompt input box.
Parallel request dispatch to all models.
Dynamic response order based on return speed.
Highlights fastest responding model.

Setup
Navigate to the React project directory.

Install dependencies:
npm install

Start the development server:
npm start

The React app assumes your backend is running at http://localhost:8081. You can change the URL in the fetchModelResponse function in App.js.


Backend (Spring Boot)

Endpoints
The backend exposes the following REST endpoints (all accept GET with encoded prompt in the path):
/api/anthropic/{prompt}
/api/deepseek/{prompt}
/api/llama/{prompt}

Model Integration
Anthropic: Uses an API key via HTTP client to call Claude 3.
DeepSeek & LLaMA: Use LM Studio on localhost to send requests to:
DeepSeek: http://localhost:1234/v1/chat/completions
LLaMA: http://localhost:1235/v1/chat/completions

Formatting Logic
Anthropic response is parsed from content[0].text.
DeepSeek & LLaMA responses are extracted from choices[0].message.content.

How to Run
Open the backend Spring Boot project in an IDE.
Start the application.
Ensure LM Studio is running with DeepSeek and LLaMA on respective ports.
Make sure your Anthropic API key is configured properly.
