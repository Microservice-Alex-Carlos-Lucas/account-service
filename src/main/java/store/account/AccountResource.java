package store.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class AccountResource implements AccountController{
    
    @Autowired
    private AccountService accountService;

    @Override
    public ResponseEntity<Void> create(AccountIn in) {
        final Account created = accountService.create(
            AccountParser.to(in)
        );

        // HATEOAS standard
        return ResponseEntity.created(ServletUriComponentsBuilder.
            fromCurrentRequest().
            path("/{id}").
            buildAndExpand(created.id()).
            toUri()
        ).build();
    }

    @Override
    public ResponseEntity<Void> delete(String id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> healthCheck() {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<AccountOut>> findAll() {
        return ResponseEntity.ok(
            AccountParser.to(accountService.findByAll())
        );
    }

    @Override
    public ResponseEntity<AccountOut> findById(String id) {
        Account out = accountService.findById(id);
        return out == null ? 
        ResponseEntity.notFound().build() :
        ResponseEntity.ok(AccountParser.to(out));
    }

    @Override
    public ResponseEntity<AccountOut> findByEmailAndPassword(AccountIn in) {
        Account out = accountService.findByEmailAndPassword(
        in.email(),
        in.password()
    );
        return out == null ? 
        ResponseEntity.notFound().build() :
        ResponseEntity.ok(AccountParser.to(out));
    }

}
