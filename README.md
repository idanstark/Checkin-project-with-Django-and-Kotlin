# Validação de Posição GPS com Django, Django Ninja e Kotlin

Este projeto implementa uma aplicação simples para **validar a posição GPS** de um dispositivo Android em relação a uma localização previamente cadastrada no banco de dados.  

A solução combina **Django** no backend e **Kotlin** no frontend Android, utilizando **Django Ninja** para criação de APIs REST.

---

## 🎯 Objetivo da Aplicação

O sistema verifica se o usuário está fisicamente presente em um **ponto geográfico autorizado**.

**Fluxo principal:**
1. Cadastro prévio de usuários e localizações no backend.
2. App Android coleta a posição GPS do dispositivo.
3. App envia os dados (usuário + coordenadas) para a API.
4. API valida se a posição corresponde à localização cadastrada.
5. Caso positivo, retorna mensagem de sucesso.

---

## 🖥️ Backend – Django + Django Admin

O **Django** foi escolhido pela agilidade no desenvolvimento e pelo **Django Admin**, que permite criar interfaces de cadastro rapidamente.

### Estrutura de Modelos
- **Usuário**: nome, email e ID único.
- **Localização**: latitude, longitude e descrição.

### APIs – Django Ninja
O **Django Ninja** foi utilizado para criação rápida e tipada de APIs REST.

**Endpoints principais:**
- `GET /usuario/{id}` → Retorna informações do usuário.
- `POST /check_in` → Recebe ID do usuário e coordenadas GPS, valida e retorna status.

**Bibliotecas utilizadas no backend:**
- `django` – Framework principal.
- `django-ninja` – Criação de APIs REST.
- `pydantic` – Validação de dados de entrada/saída.

---

## 📱 Frontend Mobile – Kotlin

O app Android foi desenvolvido em **Kotlin** para coleta e envio das coordenadas GPS ao servidor.

### Funcionalidades:
- Captura de latitude e longitude usando `FusedLocationProviderClient` (Google Play Services).
- Exibição da posição atual na tela.
- Botão **"Check-in"** que envia dados via `Retrofit` para a API Django Ninja.

**Bibliotecas utilizadas no Android:**
- `FusedLocationProviderClient` – Captura de GPS de alta precisão.
- `Retrofit` – Cliente HTTP para chamadas à API.
- `Gson` – Serialização/desserialização JSON.

---

## 🔄 Fluxo de Integração

1. Cadastro de localização no **Django Admin**.
2. App Android coleta coordenadas GPS.
3. Envio via `POST` para `/check_in`.
4. Validação no servidor comparando coordenadas.
5. Retorno JSON com status de aprovação ou reprovação.

---

## ✅ Resultado Final

- Validação em tempo real da presença do usuário na localização cadastrada.
- Retorno de mensagem de sucesso.
- Atualização do banco de dados após check-in.

💡 **Possíveis melhorias:**
- Leitura de coordenadas via **QR Code**.
- Diferentes métodos de autenticação e envio.

---

## 📌 Conclusão

Este projeto mostra, na prática, como unir:
- **Django** (backend e gerenciamento de dados)
- **APIs REST com Django Ninja**
- **Kotlin** (aplicativo Android nativo)

para resolver problemas reais como **validação de presença geográfica**, muito útil em:
- Controle de ponto
- Check-ins em eventos
- Monitoramento de equipes externas
