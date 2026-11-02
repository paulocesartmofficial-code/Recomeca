# RECOMEÇA — versão Android de testes

> **Sua vida vale mais que qualquer aposta.**

O Recomeça é um aplicativo Android em Kotlin e Jetpack Compose com ferramentas de apoio, educação financeira, diário, metas e jogos cognitivos para pessoas que desejam se afastar das apostas.

## Estado real desta versão

Esta distribuição é uma **versão de demonstração local**. Ela serve para avaliação de interface e funcionalidades no aparelho.

- Os dados do perfil e do aplicativo são armazenados localmente com Room.
- O login/cadastro ainda não autentica contas online. A tela cria um perfil de demonstração neste aparelho.
- Não existe sincronização entre dispositivos nesta versão.
- O checkout Mercado Pago ainda não está integrado. A tela VIP informa que a compra está em preparação.
- Nenhum PIX, QR Code ou cobrança fictícia é apresentado.
- O VIP não pode ser liberado pelo botão de checkout nesta versão.
- O painel administrativo está desativado. Não existe PIN mestre no cliente.
- O SOS e as informações essenciais de apoio permanecem gratuitos.

**Não anuncie esta versão como tendo cadastro online, pagamento ativo ou sincronização em nuvem.** Esses recursos precisam de backend e testes próprios antes de receber pagamentos reais.

## Funcionalidades incluídas

- Interface Android nativa com Jetpack Compose e tema escuro.
- Persistência local Room.
- Dashboard e check-in.
- Calculadora de gastos e simulações.
- Jornada e conquistas.
- Central com dez jogos/atividades cognitivas.
- Diário pessoal.
- Metas financeiras.
- Conteúdo educativo.
- SOS e plano de contingência.
- Sons ambientes e mural de afirmações.
- Exportação local de dados.
- Tela de plano VIP informativa, sem checkout falso.

## Requisitos

- Android Studio compatível com Android Gradle Plugin 9.1.1.
- JDK 21.
- Android SDK Platform 36.1 e Build Tools 36.0.0.
- Dispositivo Android 7.0 (API 24) ou superior para instalação.

## Gerar APK de teste pelo GitHub Actions

O workflow `.github/workflows/build-android-test.yml` compila o APK debug e publica o arquivo como artefato:

1. Envie esta pasta para o repositório GitHub, mantendo a pasta `.github`.
2. Abra a aba **Actions** do repositório.
3. Selecione **Build Android test APK**.
4. Clique em **Run workflow** (ou faça push na branch `main`/`master`).
5. Aguarde os testes e a compilação terminarem.
6. Baixe o artefato `recomeca-debug-apk` no final da execução.
7. Extraia o artefato e instale `app-debug.apk` no Android para testes.

O APK debug é assinado com uma chave temporária gerada no CI. Ele serve para testes e não deve ser usado como versão de atualização de uma publicação comercial.

## Gerar localmente no Windows 10

1. Extraia o ZIP do projeto.
2. Instale o Android Studio e, no SDK Manager, instale **Android SDK Platform 36.1** e **Android SDK Build-Tools 36.0.0**.
3. Confirme que o JDK 21 está disponível.
4. Dê dois cliques em `build-test-apk.bat`.
5. O script baixa Gradle 9.3.1 da distribuição oficial, cria uma chave de depuração temporária, executa os testes e compila o APK.
6. O arquivo final fica em `app\build\outputs\apk\debug\app-debug.apk`.

O repositório original não incluía os scripts e o JAR padrão do Gradle Wrapper. Por isso, o script `.bat` e o workflow do GitHub Actions usam uma instalação explícita do Gradle 9.3.1 em vez de depender de `gradlew`.

## Gerar pelo GitHub Actions

1. Envie o conteúdo desta pasta para o repositório GitHub, incluindo a pasta oculta `.github`.
2. Abra **Actions** no repositório.
3. Escolha **Build Android test APK** e clique em **Run workflow** (ou faça push em `main`/`master`).
4. Ao terminar, baixe o artefato `recomeca-debug-apk`.

## Próximas etapas antes de vender

### Contas e sincronização

Implementar Supabase Auth, confirmação de e-mail, recuperação de senha, migrações PostgreSQL e políticas RLS. Nunca confiar em permissões definidas somente no aplicativo.

### Pagamento VIP

Implementar um backend (por exemplo, Supabase Edge Functions ou serviço HTTPS próprio) que crie o checkout no Mercado Pago. O backend deve validar a notificação/webhook consultando o pagamento diretamente no provedor, conferindo valor, moeda, produto e usuário. Só então deve gravar o direito VIP. Implementar idempotência e histórico de transações.

Não incluir Access Token do Mercado Pago no APK. Não liberar VIP a partir de um botão local, resposta do cliente ou parâmetro de URL.

### Administração

Implementar papéis de administrador verificados no servidor, com auditoria e políticas de acesso. Nunca usar PIN fixo no código do aplicativo.

### Publicação

Gerar assinatura de release com keystore protegido fora do repositório, configurar política de privacidade, termos, suporte, classificação etária e validar os requisitos da loja antes de publicar.

## Observação sobre simulações financeiras

O simulador de juros compostos usa uma taxa fixa hipotética para fins educativos. Não representa a taxa atual do CDI ou do Tesouro Selic e não considera impostos, taxas, inflação ou mudanças de mercado. Resultados não são garantidos e não constituem recomendação de investimento.

## Privacidade e apoio

Os registros locais podem conter informações pessoais. Proteja o aparelho com bloqueio de tela e não compartilhe exportações sem revisar o conteúdo. O aplicativo é uma ferramenta de apoio e não substitui avaliação ou tratamento profissional. Recursos de emergência e orientação não devem ser condicionados a pagamento.
