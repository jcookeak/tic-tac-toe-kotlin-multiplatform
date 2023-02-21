<script lang="ts">

    import {Greeting} from 'tic-tac-toe-game'

    //
    // let greeter = new game.KotlinGreeter()
    let greeting = new Greeting.greeting()

    // let greeting = 'hello world!'

</script>
<h1>Welcome to SvelteKit</h1>
<p>Visit <a href="https://kit.svelte.dev">kit.svelte.dev</a> to read the documentation</p>
<p>{greeting}</p>