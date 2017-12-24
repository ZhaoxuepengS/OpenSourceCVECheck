package cn.uniview.LotusMail;

import lotus.domino.AgentBase;
import lotus.domino.NotesException;
import lotus.domino.NotesFactory;
import lotus.domino.Session;


public abstract class EclipseAgentBase extends AgentBase {

public abstract void sendMail(String text);

public Session getSession() {
try {
return NotesFactory.createSession();
} catch (NotesException e) {
throw new RuntimeException("Unable to create session", e);
}
}
}