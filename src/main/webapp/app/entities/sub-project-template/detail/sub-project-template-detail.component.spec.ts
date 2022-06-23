import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SubProjectTemplateDetailComponent } from './sub-project-template-detail.component';

describe('SubProjectTemplate Management Detail Component', () => {
  let comp: SubProjectTemplateDetailComponent;
  let fixture: ComponentFixture<SubProjectTemplateDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SubProjectTemplateDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ subProjectTemplate: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SubProjectTemplateDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SubProjectTemplateDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load subProjectTemplate on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.subProjectTemplate).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
