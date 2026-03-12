<template>
  <div class="code-editor-container">
    <div class="editor-header">
      <h3>代码编辑器</h3>
      <div class="editor-controls">
        <select v-model="selectedLanguage" @change="changeLanguage">
          <option value="javascript">JavaScript</option>
          <option value="typescript">TypeScript</option>
          <option value="python">Python</option>
          <option value="java">Java</option>
          <option value="vue">Vue</option>
          <option value="html">HTML</option>
          <option value="css">CSS</option>
          <option value="json">JSON</option>
        </select>
        <button @click="formatCode" class="format-btn">格式化</button>
        <button @click="runCode" class="run-btn">运行</button>
      </div>
    </div>
    <div ref="editorContainer" class="editor-content"></div>
    <div v-if="output" class="output-panel">
      <h4>输出结果：</h4>
      <pre>{{ output }}</pre>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'

// 编辑器相关状态
const editorContainer = ref<HTMLDivElement>()
const selectedLanguage = ref('javascript')
const output = ref('')
let editor: any = null

// 示例代码 - 使用数组方式避免标签解析错误
const codeExamples = {
  javascript: `// JavaScript 示例
function greet(name) {
  return \`Hello, \${name}!\`;
}

console.log(greet("World"));`,
  typescript: `// TypeScript 示例
interface User {
  name: string;
  age: number;
}

function createUser(name: string, age: number): User {
  return { name, age };
}

const user = createUser("Alice", 30);
console.log(user);`,
  python: `# Python 示例
def fibonacci(n):
    if n <= 1:
        return n
    return fibonacci(n-1) + fibonacci(n-2)

print(fibonacci(10))`,
  java: `// Java 示例
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}`,
  vue: [
    '<template>',
    '  <div class="hello">',
    '    <h1>{{ message }}</h1>',
    '  </div>',
    '</template>',
    '',
    '<script>',
    'export default {',
    '  data() {',
    '    return {',
    '      message: \'Hello Vue!\'',
    '    }',
    '  }',
    '}',
    '<\/script>'
  ].join('\n'),
  html: `<!DOCTYPE html>
<html>
<head>
    <title>示例页面</title>
</head>
<body>
    <h1>Hello World!</h1>
</body>
</html>`,
  css: `/* CSS 示例 */
.hello {
  color: blue;
  font-size: 24px;
}

h1 {
  text-align: center;
}`,
  json: `{
  "name": "示例项目",
  "version": "1.0.0",
  "dependencies": {
    "vue": "^3.0.0"
  }
}`
}

// 初始化编辑器
const initEditor = () => {
  if (!editorContainer.value) return

  // 创建简单的代码编辑器
  const textarea = document.createElement('textarea')
  textarea.style.width = '100%'
  textarea.style.height = '400px'
  textarea.style.border = '1px solid #ccc'
  textarea.style.padding = '10px'
  textarea.style.fontFamily = 'Monaco, Consolas, "Courier New", monospace'
  textarea.style.fontSize = '14px'
  textarea.style.lineHeight = '1.5'
  textarea.style.resize = 'vertical'
  textarea.value = codeExamples[selectedLanguage.value as keyof typeof codeExamples]

  editorContainer.value.appendChild(textarea)
  editor = textarea

  // 添加语法高亮（简单版本）
  textarea.addEventListener('input', () => {
    // 这里可以添加简单的语法高亮逻辑
  })
}

// 切换语言
const changeLanguage = () => {
  if (editor) {
    editor.value = codeExamples[selectedLanguage.value as keyof typeof codeExamples]
  }
}

// 格式化代码
const formatCode = () => {
  if (!editor) return

  try {
    if (selectedLanguage.value === 'json') {
      const parsed = JSON.parse(editor.value)
      editor.value = JSON.stringify(parsed, null, 2)
    } else {
      // 简单的格式化（缩进处理）
      const lines = editor.value.split('\n')
      let formatted = ''
      let indent = 0

      for (const line of lines) {
        const trimmed = line.trim()
        if (trimmed.endsWith('{') || trimmed.endsWith('[')) {
          formatted += '  '.repeat(indent) + trimmed + '\n'
          indent++
        } else if (trimmed.startsWith('}') || trimmed.startsWith(']')) {
          indent--
          formatted += '  '.repeat(indent) + trimmed + '\n'
        } else {
          formatted += '  '.repeat(indent) + trimmed + '\n'
        }
      }

      editor.value = formatted.trim()
    }
  } catch (error) {
    console.error('格式化失败:', error)
  }
}

// 运行代码
const runCode = () => {
  if (!editor) return

  const code = editor.value

  try {
    // 简单的代码执行（仅支持JavaScript）
    if (selectedLanguage.value === 'javascript') {
      // 使用eval执行代码（注意：生产环境中不推荐使用eval）
      const result = eval(code)
      output.value = result !== undefined ? String(result) : '代码执行完成'
    } else {
      output.value = `当前语言 ${selectedLanguage.value} 的代码执行功能正在开发中...`
    }
  } catch (error: any) {
    output.value = `执行错误: ${error.message}`
  }
}

onMounted(() => {
  initEditor()
})

onUnmounted(() => {
  if (editor && editor.parentNode) {
    editor.parentNode.removeChild(editor)
  }
})
</script>

<style scoped>
.code-editor-container {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  overflow: hidden;
}

.editor-header {
  background: #f5f5f5;
  padding: 10px 15px;
  border-bottom: 1px solid #e0e0e0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.editor-header h3 {
  margin: 0;
  color: #333;
}

.editor-controls {
  display: flex;
  gap: 10px;
  align-items: center;
}

.editor-controls select {
  padding: 5px 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  background: white;
}

.editor-controls button {
  padding: 5px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 12px;
}

.format-btn {
  background: #007acc;
  color: white;
}

.format-btn:hover {
  background: #005a9e;
}

.run-btn {
  background: #28a745;
  color: white;
}

.run-btn:hover {
  background: #1e7e34;
}

.editor-content {
  flex: 1;
  position: relative;
}

.output-panel {
  background: #f8f9fa;
  border-top: 1px solid #e0e0e0;
  padding: 15px;
  max-height: 200px;
  overflow-y: auto;
}

.output-panel h4 {
  margin: 0 0 10px 0;
  color: #333;
}

.output-panel pre {
  margin: 0;
  white-space: pre-wrap;
  word-wrap: break-word;
  font-family: Monaco, Consolas, "Courier New", monospace;
  font-size: 12px;
  color: #333;
}
</style>