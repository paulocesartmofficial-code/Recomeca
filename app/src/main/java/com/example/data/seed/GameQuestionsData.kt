package com.example.data.seed

data class QuizQuestion(
    val id: Int,
    val category: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class LogicPuzzle(
    val id: Int,
    val title: String,
    val prompt: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class ScrambleWord(
    val id: Int,
    val scrambled: String,
    val original: String,
    val hint: String,
    val category: String
)

object GameQuestionsData {

    val quizQuestions: List<QuizQuestion> = listOf(
        // História
        QuizQuestion(
            id = 1,
            category = "História",
            question = "Em que ano ocorreu a Proclamação da República no Brasil?",
            options = listOf("1822", "1889", "1930", "1808"),
            correctIndex = 1,
            explanation = "A Proclamação da República ocorreu no dia 15 de novembro de 1889, liderada pelo Marechal Deodoro da Fonseca."
        ),
        QuizQuestion(
            id = 2,
            category = "História",
            question = "Qual civilização antiga construiu as pirâmides de Gizé?",
            options = listOf("Maias", "Mesopotâmicos", "Egípcios", "Gregos"),
            correctIndex = 2,
            explanation = "Os egípcios construíram as famosas pirâmides durante o Reino Antigo, por volta de 2500 a.C."
        ),
        QuizQuestion(
            id = 3,
            category = "História",
            question = "Quem foi o líder sul-africano que lutou contra o Apartheid e recebeu o Nobel da Paz?",
            options = listOf("Nelson Mandela", "Desmond Tutu", "Kofi Annan", "Steve Biko"),
            correctIndex = 0,
            explanation = "Nelson Mandela passou 27 anos na prisão e tornou-se o primeiro presidente negro da África do Sul após o fim do Apartheid."
        ),
        QuizQuestion(
            id = 4,
            category = "História",
            question = "A queda da Bastilha em 1789 é o marco inicial de qual evento histórico?",
            options = listOf("Revolução Industrial", "Revolução Francesa", "Guerra Fria", "Primeira Guerra"),
            correctIndex = 1,
            explanation = "A tomada da Bastilha pelos cidadãos de Paris marcou o início simbólico da Revolução Francesa."
        ),
        QuizQuestion(
            id = 5,
            category = "História",
            question = "Em que ano terminou a Segunda Guerra Mundial?",
            options = listOf("1918", "1939", "1945", "1950"),
            correctIndex = 2,
            explanation = "A Segunda Guerra Mundial encerrou-se em 1945 após a capitulação alemã e japonesa."
        ),
        QuizQuestion(
            id = 6,
            category = "História",
            question = "Quem foi a primeira mulher a sobrevoar o Oceano Atlântico em voo solo?",
            options = listOf("Amelia Earhart", "Valentina Tereshkova", "Marie Curie", "Ada Lovelace"),
            correctIndex = 0,
            explanation = "Amelia Earhart realizou o histórico voo transatlântico solo em 1932."
        ),

        // Ciência
        QuizQuestion(
            id = 7,
            category = "Ciência",
            question = "Qual é o elemento químico mais abundante no universo observável?",
            options = listOf("Oxigênio", "Hélio", "Hidrogênio", "Carbono"),
            correctIndex = 2,
            explanation = "O hidrogênio compõe aproximadamente 75% da massa bariônica do universo."
        ),
        QuizQuestion(
            id = 8,
            category = "Ciência",
            question = "Qual órgão do corpo humano consome mais energia percentual em repouso?",
            options = listOf("Coração", "Cérebro", "Fígado", "Rins"),
            correctIndex = 1,
            explanation = "Apesar de representar apenas cerca de 2% do peso corporal, o cérebro humano consome cerca de 20% de toda a energia do corpo."
        ),
        QuizQuestion(
            id = 9,
            category = "Ciência",
            question = "Quem propôs a teoria da relatividade geral?",
            options = listOf("Isaac Newton", "Albert Einstein", "Niels Bohr", "Galileu Galilei"),
            correctIndex = 1,
            explanation = "Albert Einstein publicou a Teoria da Relatividade Geral em 1915, revolucionando nossa compreensão de gravidade e espaço-tempo."
        ),
        QuizQuestion(
            id = 10,
            category = "Ciência",
            question = "Qual é a principal função dos glóbulos vermelhos no sangue?",
            options = listOf("Combater infecções", "Transportar oxigênio", "Produzir anticorpos", "Coagular o sangue"),
            correctIndex = 1,
            explanation = "Os eritrócitos contêm hemoglobina, responsável por capturar oxigênio nos pulmões e transportá-lo aos tecidos."
        ),
        QuizQuestion(
            id = 11,
            category = "Ciência",
            question = "A que velocidade aproximada a luz viaja no vácuo?",
            options = listOf("300.000 km/s", "150.000 km/s", "1.000.000 km/s", "30.000 km/s"),
            correctIndex = 0,
            explanation = "A velocidade da luz no vácuo é de exatamente 299.792.458 m/s, frequentemente aproximada para 300.000 km/s."
        ),

        // Geografia
        QuizQuestion(
            id = 12,
            category = "Geografia",
            question = "Qual é o maior oceano da Terra em área de superfície?",
            options = listOf("Atlântico", "Índico", "Pacífico", "Ártico"),
            correctIndex = 2,
            explanation = "O Oceano Pacífico cobre mais de 165 milhões de quilômetros quadrados, sendo maior que todas as massas terrestres combinadas."
        ),
        QuizQuestion(
            id = 13,
            category = "Geografia",
            question = "Qual é a montanha mais alta do mundo em relação ao nível do mar?",
            options = listOf("K2", "Monte Everest", "Aconcágua", "Monte Kilimanjaro"),
            correctIndex = 1,
            explanation = "O Monte Everest atinge 8.848,86 metros de altitude na cordilheira do Himalaia."
        ),
        QuizQuestion(
            id = 14,
            category = "Geografia",
            question = "Qual país possui a maior floresta tropical do planeta?",
            options = listOf("Brasil", "Indonésia", "Congo", "Peru"),
            correctIndex = 0,
            explanation = "O Brasil abriga cerca de 60% da Floresta Amazônica, a maior floresta tropical úmida do mundo."
        ),
        QuizQuestion(
            id = 15,
            category = "Geografia",
            question = "Qual é a capital oficial do Canadá?",
            options = listOf("Toronto", "Vancouver", "Montreal", "Ottawa"),
            correctIndex = 3,
            explanation = "Ottawa é a capital política e administrativa do Canadá, localizada na província de Ontário."
        ),

        // Tecnologia
        QuizQuestion(
            id = 16,
            category = "Tecnologia",
            question = "Quem é amplamente considerado a primeira pessoa programadora da história?",
            options = listOf("Alan Turing", "Ada Lovelace", "Grace Hopper", "Charles Babbage"),
            correctIndex = 1,
            explanation = "Ada Lovelace escreveu em 1843 o primeiro algoritmo para a máquina analítica de Babbage, antecipando a computação moderna."
        ),
        QuizQuestion(
            id = 17,
            category = "Tecnologia",
            question = "O que significa a sigla 'HTTP' usada na internet?",
            options = listOf(
                "HyperText Transfer Protocol",
                "High Tech Telecommunication Program",
                "Home Tool Transfer Page",
                "HyperTransfer Text Project"
            ),
            correctIndex = 0,
            explanation = "HTTP significa HyperText Transfer Protocol, protocolo base para troca de dados na World Wide Web criada por Tim Berners-Lee."
        ),
        QuizQuestion(
            id = 18,
            category = "Tecnologia",
            question = "Qual linguagem de programação foi criada por James Gosling na Sun Microsystems em 1995?",
            options = listOf("Python", "Java", "C++", "Ruby"),
            correctIndex = 1,
            explanation = "Java foi criada com a premissa de 'escreva uma vez, execute em qualquer lugar' (WORA)."
        ),
        QuizQuestion(
            id = 19,
            category = "Tecnologia",
            question = "Em que sistema operacional para celulares o Kernel Linux serve como base fundamental?",
            options = listOf("iOS", "Android", "Symbian", "Blackberry OS"),
            correctIndex = 1,
            explanation = "O Android utiliza o núcleo (kernel) Linux para gerenciar hardware, memória e processos."
        ),

        // Cultura
        QuizQuestion(
            id = 20,
            category = "Cultura",
            question = "Quem escreveu o clássico romance brasileiro 'Dom Casmurro'?",
            options = listOf("José de Alencar", "Machado de Assis", "Clarice Lispector", "Monteiro Lobato"),
            correctIndex = 1,
            explanation = "Machado de Assis publicou Dom Casmurro em 1899, imortalizando a personagem Capitu e o narrador Bento Santiago."
        ),
        QuizQuestion(
            id = 21,
            category = "Cultura",
            question = "Qual artista renascentista pintou o teto da Capela Sistina no Vaticano?",
            options = listOf("Leonardo da Vinci", "Michelangelo", "Rafael Sanzio", "Donatello"),
            correctIndex = 1,
            explanation = "Michelangelo Buonarroti dedicou quatro anos (1508-1512) à pintura dos afrescos magistrais da Capela Sistina."
        ),
        QuizQuestion(
            id = 22,
            category = "Cultura",
            question = "Qual compositor clássico continuou compondo grandes sinfonias mesmo após perder totalmente a audição?",
            options = listOf("Mozart", "Beethoven", "Bach", "Chopin"),
            correctIndex = 1,
            explanation = "Ludwig van Beethoven compôs sua monumental Nona Sinfonia quando já estava completamente surdo."
        ),

        // Esportes
        QuizQuestion(
            id = 23,
            category = "Esportes",
            question = "Qual é o único país a ter participado de todas as edições da Copa do Mundo de Futebol da FIFA?",
            options = listOf("Alemanha", "Itália", "Brasil", "Argentina"),
            correctIndex = 2,
            explanation = "A Seleção Brasileira é a única a ter disputado todas as edições do Mundial desde 1930."
        ),
        QuizQuestion(
            id = 24,
            category = "Esportes",
            question = "Quantos pontos vale um arremesso no basquete convertido além do arco de perímetro?",
            options = listOf("1 ponto", "2 pontos", "3 pontos", "4 pontos"),
            correctIndex = 2,
            explanation = "Arremessos atrás da linha de três pontos valem três pontos quando convertidos com sucesso."
        ),
        QuizQuestion(
            id = 25,
            category = "Esportes",
            question = "Em que modalidade olímpica Ayrton Senna é considerado uma lenda mundial?",
            options = listOf("Automobilismo", "Atletismo", "Natação", "Ciclismo"),
            correctIndex = 0,
            explanation = "Ayrton Senna foi tricampeão mundial de Fórmula 1 e um dos maiores ícones esportivos de todos os tempos."
        )
    )

    val logicPuzzles: List<LogicPuzzle> = listOf(
        LogicPuzzle(
            id = 1,
            title = "A Sequência dos Quadrados",
            prompt = "Qual é o próximo número da sequência: 1, 4, 9, 16, 25, ... ?",
            options = listOf("30", "36", "49", "32"),
            correctIndex = 1,
            explanation = "A sequência é formada pelos quadrados perfeitos: 1², 2², 3², 4², 5², logo o próximo é 6² = 36."
        ),
        LogicPuzzle(
            id = 2,
            title = "O Enigma dos Irmãos",
            prompt = "Um homem tem 4 filhas. Cada filha tem 1 irmão. Quantos filhos esse homem tem no total?",
            options = listOf("8 filhos", "5 filhos", "6 filhos", "4 filhos"),
            correctIndex = 1,
            explanation = "Todas as 4 irmãs compartilham o mesmo irmão, portanto são 4 filhas e 1 filho = 5 filhos no total."
        ),
        LogicPuzzle(
            id = 3,
            title = "A Sequência de Fibonacci",
            prompt = "Descubra o termo que completa a série: 2, 3, 5, 8, 13, ... ?",
            options = listOf("18", "21", "20", "24"),
            correctIndex = 1,
            explanation = "Cada termo é a soma dos dois anteriores: 8 + 13 = 21."
        ),
        LogicPuzzle(
            id = 4,
            title = "O Relógio Atrasado",
            prompt = "Se agora são 15:45 e um relógio atrasa 3 minutos a cada hora, quantos minutos ele terá atrasado após 8 horas?",
            options = listOf("18 minutos", "24 minutos", "21 minutos", "30 minutos"),
            correctIndex = 1,
            explanation = "Multiplica-se 3 minutos por hora × 8 horas = 24 minutos de atraso acumulado."
        ),
        LogicPuzzle(
            id = 5,
            title = "O Padrão Multiplicador",
            prompt = "Qual é o próximo número: 3, 6, 12, 24, 48, ... ?",
            options = listOf("96", "84", "72", "60"),
            correctIndex = 0,
            explanation = "Cada número é exatamente o dobro do anterior: 48 × 2 = 96."
        )
    )

    val scrambleWords: List<ScrambleWord> = listOf(
        ScrambleWord(
            id = 1,
            scrambled = "BERLIDAD",
            original = "LIBERDADE",
            hint = "O que você conquista ao não ser prisioneiro de impulsos",
            category = "Superação"
        ),
        ScrambleWord(
            id = 2,
            scrambled = "ANFANÇI",
            original = "FINANCAS",
            hint = "Área da sua vida que se reorganiza dia após dia",
            category = "Recuperação"
        ),
        ScrambleWord(
            id = 3,
            scrambled = "SOPORPITO",
            original = "PROPOSITO",
            hint = "A razão maior pela qual vale a pena viver e lutar",
            category = "Vida"
        ),
        ScrambleWord(
            id = 4,
            scrambled = "ZAPLAUTRAN",
            original = "TRANQUILIDADE",
            hint = "A sensação impagável de dormir com a consciência limpa",
            category = "Paz"
        ),
        ScrambleWord(
            id = 5,
            scrambled = "NIDATERAÇMEO",
            original = "DETERMINACAO",
            hint = "A força que faz você dizer 'apenas por hoje eu não aposto'",
            category = "Atitude"
        ),
        ScrambleWord(
            id = 6,
            scrambled = "SEREPANÇA",
            original = "ESPERANCA",
            hint = "A certeza de que dias melhores estão sendo construídos",
            category = "Sentimento"
        )
    )

    val defaultAchievements = listOf(
        com.example.data.model.Achievement("ach_1", "Primeiro Dia Vencido", "Completou seu primeiro dia sem apostar. O primeiro passo da sua nova história.", "🌱", 1, 50),
        com.example.data.model.Achievement("ach_7", "Uma Semana de Clareza", "7 dias limpos. Sua química cerebral começa a se reorganizar.", "🛡️", 7, 100),
        com.example.data.model.Achievement("ach_15", "Fortaleza Mental", "15 dias de consistência e autocontrole inabalável.", "⚡", 15, 150),
        com.example.data.model.Achievement("ach_30", "Um Mês de Liberdade", "30 dias longe das apostas. Uma nova identidade se consolidou.", "🏆", 30, 250),
        com.example.data.model.Achievement("ach_60", "Dois Meses de Paz", "60 dias preservando seu dinheiro e sua tranquilidade diária.", "💎", 60, 400),
        com.example.data.model.Achievement("ach_90", "Trimestre Inquebrável", "90 dias de sobriedade financeira e foco nos seus objetivos.", "🌟", 90, 600),
        com.example.data.model.Achievement("ach_180", "Meio Ano Vitorioso", "180 dias de renascimento pessoal e familiar.", "👑", 180, 1000),
        com.example.data.model.Achievement("ach_365", "Um Ano de Nova Vida", "365 dias. Você provou que a sua vida vale mais que qualquer aposta.", "☀️", 365, 2000)
    )
}
