package dev.marchuk.statemachine.config.statemachine;

import dev.marchuk.statemachine.domain.Event;
import dev.marchuk.statemachine.domain.ActivityState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

import java.util.Optional;

@Slf4j
public class ActivityStateMachineApplicationListener implements StateMachineListener<ActivityState, Event> {
    @Override
    public void stateChanged(State<ActivityState, Event> from, State<ActivityState, Event> to) {
        var fromState = Optional.ofNullable(from)
                .map(State::getId)
                .orElse(null);
        log.debug("state changed ({} -> {})", fromState, to.getId());
    }

    @Override
    public void stateEntered(State<ActivityState, Event> state) {
        log.debug("state entered: {}", state.getId());
    }

    @Override
    public void stateExited(State<ActivityState, Event> state) {
        log.debug("state exited: {}", state.getId());
    }

    @Override
    public void eventNotAccepted(Message<Event> message) {
        log.info("Event not accepted: {}", message.getPayload().name());
    }

    @Override
    public void transition(Transition<ActivityState, Event> transition) {
        var fromState = Optional.ofNullable(transition.getSource())
                .map(State::getId)
                .orElse(null);
        log.info("transition from {} to {}", fromState, transition.getTarget().getId());
    }

    @Override
    public void transitionStarted(Transition<ActivityState, Event> transition) {
        log.debug("transition started");
    }

    @Override
    public void transitionEnded(Transition<ActivityState, Event> transition) {
        log.debug("transition ended");
    }

    @Override
    public void stateMachineStarted(StateMachine<ActivityState, Event> stateMachine) {
        log.info("state machine started (UUID = {})", stateMachine.getUuid());
    }

    @Override
    public void stateMachineStopped(StateMachine<ActivityState, Event> stateMachine) {
        log.debug("state machine stopped (UUID = {})", stateMachine.getUuid());
    }

    @Override
    public void stateMachineError(StateMachine<ActivityState, Event> stateMachine, Exception e) {
        log.debug("state machine error(UUID = {})", stateMachine.getUuid(), e);
    }

    @Override
    public void extendedStateChanged(Object key, Object value) {
        log.debug("extended state changed: {} -> {}", key, value);
    }

    @Override
    public void stateContext(StateContext<ActivityState, Event> stateContext) {
        log.debug("state context");
    }
}
